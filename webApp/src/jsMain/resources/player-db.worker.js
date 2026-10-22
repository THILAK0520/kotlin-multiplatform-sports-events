importScripts("sql-wasm.js");

const DATABASE_NAME = "sports-events-sqldelight";
const DATABASE_STORE = "databases";
const DATABASE_KEY = "sports-events";

let db;
let inTransaction = false;
let hasUncommittedChanges = false;

const indexedDbReady = new Promise((resolve, reject) => {
  const request = indexedDB.open(DATABASE_NAME, 1);
  request.onupgradeneeded = () => {
    request.result.createObjectStore(DATABASE_STORE);
  };
  request.onsuccess = () => resolve(request.result);
  request.onerror = () => reject(request.error);
});

async function readSnapshot() {
  const storage = await indexedDbReady;
  return new Promise((resolve, reject) => {
    const request = storage
      .transaction(DATABASE_STORE, "readonly")
      .objectStore(DATABASE_STORE)
      .get(DATABASE_KEY);
    request.onsuccess = () => resolve(request.result ?? null);
    request.onerror = () => reject(request.error);
  });
}

async function saveSnapshot() {
  try {
    const storage = await indexedDbReady;
    const bytes = db.export();
    await new Promise((resolve, reject) => {
      const transaction = storage.transaction(DATABASE_STORE, "readwrite");
      transaction.objectStore(DATABASE_STORE).put(bytes.buffer, DATABASE_KEY);
      transaction.oncomplete = resolve;
      transaction.onerror = () => reject(transaction.error);
      transaction.onabort = () => reject(transaction.error);
    });
  } catch (error) {
    // The SQL operation still succeeds for this session if browser storage is unavailable.
    console.warn("Could not persist the SportsHub database in IndexedDB", error);
  }
}

async function initializeDatabase() {
  const SQL = await initSqlJs({
    locateFile: () => new URL("sql-wasm.wasm", self.location.href).toString()
  });
  const snapshot = await readSnapshot();
  db = snapshot ? new SQL.Database(new Uint8Array(snapshot)) : new SQL.Database();
}

const databaseReady = initializeDatabase();

function isMutation(sql) {
  return /^\s*(INSERT|UPDATE|DELETE|REPLACE|CREATE|DROP|ALTER|VACUUM|PRAGMA)\b/i.test(sql);
}

self.onmessage = async (event) => {
  const data = event.data;
  try {
    await databaseReady;
    let results;

    switch (data && data.action) {
      case "exec": {
        if (!data.sql) throw new Error("exec: Missing query string");
        results = db.exec(data.sql, data.params)[0] ?? { values: [] };
        if (isMutation(data.sql)) {
          hasUncommittedChanges = true;
          if (!inTransaction) {
            await saveSnapshot();
            hasUncommittedChanges = false;
          }
        }
        break;
      }
      case "begin_transaction":
        db.exec("BEGIN TRANSACTION;");
        inTransaction = true;
        results = [];
        break;
      case "end_transaction":
        db.exec("END TRANSACTION;");
        inTransaction = false;
        if (hasUncommittedChanges) {
          await saveSnapshot();
          hasUncommittedChanges = false;
        }
        results = [];
        break;
      case "rollback_transaction":
        db.exec("ROLLBACK TRANSACTION;");
        inTransaction = false;
        hasUncommittedChanges = false;
        results = [];
        break;
      default:
        throw new Error(`Unsupported action: ${data && data.action}`);
    }

    self.postMessage({ id: data.id, results });
  } catch (error) {
    self.postMessage({ id: data && data.id, error });
  }
};

let accountId = null,
  accountName = "";
const $ = (id) => document.getElementById(id);
async function request(url, method = "GET", data = null) {
  let r = await fetch(url, {
    method,
    headers: { "Content-Type": "application/json" },
    ...(data ? { body: JSON.stringify(data) } : {}),
  });
  let j = await r.json().catch(() => ({}));
  if (!r.ok) throw Error(j.error || "Request failed");
  return j;
}
function msg(id, t, ok = false) {
  $(id).textContent = t;
  $(id).className = "msg " + (ok ? "success" : "");
}
async function login() {
  try {
    let x = await request("/api/login", "POST", {
      username: $("username").value,
      password: $("password").value,
    });
    accountId = x.id;
    accountName = x.name || x.username;
    $("loginPanel").classList.add("hidden");
    $("appPanel").classList.remove("hidden");
    $("welcome").textContent = "Welcome, " + accountName;
    msg("appMessage", "Login successful", true);
    show("balance");
  } catch (e) {
    msg("loginMessage", e.message);
  }
}
function logout() {
  accountId = null;
  $("appPanel").classList.add("hidden");
  $("loginPanel").classList.remove("hidden");
  $("password").value = "";
  msg("loginMessage", "Logged out", true);
}
function showRegister() {
  $("loginPanel").classList.add("hidden");
  $("registerPanel").classList.remove("hidden");
}
function backLogin() {
  $("registerPanel").classList.add("hidden");
  $("loginPanel").classList.remove("hidden");
}
async function register() {
  try {
    let a = await request("/api/register", "POST", {
      customerName: $("rname").value,
      email: $("remail").value,
      mobile: $("rmobile").value,
      username: $("ruser").value,
      password: $("rpass").value,
    });
    msg("registerMessage", "Account created. Account ID: " + a.id, true);
  } catch (e) {
    msg("registerMessage", e.message);
  }
}
function show(type) {
  let w = $("work");
  $("appMessage").textContent = "";
  if (type === "deposit" || type === "withdraw") {
    w.innerHTML = `<h3>${type === "deposit" ? "Deposit" : "Withdraw"}</h3><input id="amount" type="number" step="0.01" placeholder="Amount"><button id="actionBtn" onclick="money('${type}')">Submit</button>`;
  } else if (type === "balance") {
    w.innerHTML = `<h3>Balance enquiry</h3><button id="balanceBtn" onclick="balance()">Check balance</button><p id="balanceResult"></p>`;
  } else if (type === "transfer") {
    w.innerHTML = `<h3>Fund transfer</h3><input id="receiver" type="number" placeholder="Receiver account ID"><input id="amount" type="number" step="0.01" placeholder="Amount"><button id="transferBtn" onclick="transfer()">Transfer</button>`;
  } else if (type === "history") {
    w.innerHTML = `<h3>Transaction history</h3><button id="historyBtn" onclick="history()">Load history</button><div id="historyResult"></div>`;
  }
}
async function money(type) {
  try {
    let a = await request(`/api/accounts/${accountId}/${type}`, "POST", {
      amount: Number($("amount").value),
    });
    msg("appMessage", `${type} successful. Balance: ₹${a.balance}`, true);
  } catch (e) {
    msg("appMessage", e.message);
  }
}
async function balance() {
  try {
    let a = await request(`/api/accounts/${accountId}`);
    $("balanceResult").textContent = "Current balance: ₹" + a.balance;
    msg("appMessage", "Balance fetched", true);
  } catch (e) {
    msg("appMessage", e.message);
  }
}
async function transfer() {
  try {
    let x = await request("/api/transfer", "POST", {
      from: Number(accountId),
      to: Number($("receiver").value),
      amount: Number($("amount").value),
    });
    msg("appMessage", x.message, true);
  } catch (e) {
    msg("appMessage", e.message);
  }
}
async function history() {
  try {
    let a = await request(`/api/accounts/${accountId}/transactions`);
    $("historyResult").innerHTML =
      "<table><tr><th>Type</th><th>Amount</th><th>Details</th></tr>" +
      a
        .map(
          (t) =>
            `<tr><td>${t.type}</td><td>${t.amount}</td><td>${t.details}</td></tr>`,
        )
        .join("") +
      "</table>";
  } catch (e) {
    msg("appMessage", e.message);
  }
}

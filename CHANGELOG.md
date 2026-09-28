# Changelog

All notable changes to OpenFluxAndroid. Format loosely follows
[Keep a Changelog](https://keepachangelog.com/en/1.1.0/).

## [2.8.0] - 2026-09-29 (meepo161/OpenFluxAndroid)

The fork on p1neappleXpress 2.1.0: links read and made by the core, a
classic profile as the exit node, one protocol for every client.

### Added

- Developer mode: ten taps on the app version in Settings → About show the
  «Аккаунты» tab (a switch there hides it again). In developer mode the node
  wizard also offers to sign in to Yandex and make the document, in the
  Accounts tab's sign-in window; otherwise it takes your own link.

### Fixed

- The node wizard's sign-in menus («другой способ получить код») no longer
  close as the keyboard opens: the page opens full screen in the Accounts
  tab's sign-in window instead of inside the wizard's scrolling step.

### Changed

- Bumps `OpenFlux` to the fork's [`57c69d7`](https://github.com/meepo161/OpenFlux/commit/57c69d706a71cd77f1951efeb8b438d275e1df86)
  (`v0.3.0`, node core `node-v1.2.0`) and `shared` to
  [`806f0b4`](https://github.com/meepo161/OpenFluxClientShared/commit/806f0b48de9dee598e91810f11a756f56ca578e9).

## [2.7.0] - 2026-09-28 (meepo161/OpenFluxAndroid)

### Added

- «Аккаунты»: sign in once to Yandex or Mail.ru in the built-in browser; the
  app keeps only the session (owner-only `accounts.json`, never in backups,
  logs, QR codes or links) and shows each service's status — signed in (with
  the login and when it was last checked), expired, or asking for a check.
  Sessions are rechecked every 30 minutes without the browser; «Войти заново»
  is one button, on the card and in a banner on Home when a profile needs it.
- «Создать документ» makes the channel's document with the saved account, no
  `cookies.txt` export: Yandex — a document on Disk with editing by link;
  Mail.ru — a document in Cloud (`/openflux`), published and switched to
  editing by link, then checked the way the core opens it (anonymous
  `r7/edit`). The same button sits under a document field in the profile
  editor; the node wizard uses the saved Yandex account too.
- The Yandex sign-in goes into the core's cookie store before it connects, and
  to your own node (from the wizard) over the tunnel, again after a fresh
  sign-in. Someone else's node gets it only by hand, after a warning. The
  Mail.ru account is never put into the core: its transport opens the
  document anonymously and Cloud refuses that request with the account's
  cookies.
- Cups.online: «Сгенерировать комнаты» in the profile editor opens four rooms
  without a node; the node given the same string joins them.

### Fixed

- Creating a Yandex document failed with «Failed to fetch»: Disk now
  redirects `/editnew` to `docs.yandex.ru`, where the browser refused a
  readable (CORS) request. Affected the node wizard as well.
- The built-in browser pages saw a 0 px viewport (the WebView was sized to
  its content): VK ID for Mail.ru stayed blank, and Yandex's «Resend code»
  sheet (SMS, Telegram) closed as soon as it opened. The sign-in now takes
  the whole screen on a phone.
- The core library is linked for 16 KB pages; Android 15+ no longer runs the
  app in a compatibility mode with a warning on every start.

## [2.6.0] - 2026-09-28 (meepo161/OpenFluxAndroid)

The first release of the meepo161 fork: the app, its update check, the core
submodule and the node wizard's core come from the fork's repositories
(meepo161/OpenFlux, meepo161/OpenFluxClientShared, meepo161/OpenFluxAndroid).

### Added

- «Своя нода»: step 1 asks whose core the server gets — the fork's
  (`meepo161/OpenFlux`, default) or the original (`p1neappleXpress/OpenFlux`);
  the node's auto-update then follows that repository.
- The core is built into the APK (fork `v0.2.0`); the choice of the
  node's core is in the wizard.

### From p1neappleXpress/OpenFluxAndroid (not yet released there)

### Added

- Developer mode (ten taps on the app version in Settings → About) shows
  the «Аккаунты» tab: sign in once to Yandex or Mail.ru in a WebView; the
  app keeps only the session (owner-only `accounts.json`, never in backups,
  logs, QR codes or links) and shows each service's status — signed in,
  expired, or asking for a check. Sessions are rechecked every 30 minutes;
  «Войти заново» is one button.
- «Создать документ» makes the channel's document with the saved account:
  Yandex — a document on Disk with editing by link; Mail.ru — a document in
  Cloud (`/openflux`), published and switched to editing by link. In
  developer mode the node wizard offers it too, in the same sign-in window.
- The Yandex sign-in goes into the core's cookie store before it connects,
  and to your own node (from the wizard) over the tunnel. The Mail.ru account
  is never put into the core: its transport opens the document anonymously.
- Cups.online: «Сгенерировать комнаты» in the profile editor opens four
  rooms without a node.
- «Своя нода»: a new channel is no longer Yandex-only. Step 2 picks any mix
  of a Yandex document (your own link), a Mail.ru public document and
  cups.online rooms (created automatically), with direct always on as the
  backup; the link and the saved profile carry all of them.
- «Автообновление ядра» on the plan step (on by default): the server's
  `openflux-node-update.timer` checks the newest `node-v*` release every
  6 hours, verifies it against the release's `node-install.sh` and
  `SHA256SUMS`, restarts the channels and rolls back if one does not stay
  up.
- Bumps `OpenFlux` to [`ee7cf56`](https://github.com/p1neappleXpress/OpenFlux/commit/ee7cf56d27549018d5fc3f6a5a31445fff55380c)
  and `shared` to [`b23354a`](https://github.com/p1neappleXpress/OpenFluxClientShared/commit/b23354ab2ecf8968eca8a3d1b7b66f6f74be07aa).

## [2.1.0] - 2026-09-28

### Changed

- Share links are read and made by the core, the way every client does:
  bumps `OpenFlux` to [`2ec01a5`](https://github.com/p1neappleXpress/OpenFlux/commit/2ec01a5) (core 0.2.0) and `shared` to
  [`ae5e59a`](https://github.com/p1neappleXpress/OpenFluxClientShared/commit/ae5e59a).
  - A link that picked up line breaks, spaces, non-breaking or zero-width
    characters, padding or the standard base64 alphabet on the way imports,
    as on iOS, instead of «Ссылка повреждена».
  - The same profile makes the same link on Desktop, Android and iOS; the
    core names the encryption context of a Session link, the app no longer
    derives it.
  - A refused link says why: not a link, cut short, letters changed case,
    unknown transport, key too short, and so on.
  - The node wizard installs `node-v1.1.0`, the node build of core 0.2.0.
- The node wizard's document step takes the link of a document you
  created; the button that signed in to Yandex and created one is gone.
- The release notes show this changelog.

### Fixed

- Clients and nodes built from different trees now connect: bumps `OpenFlux`
  to [`f8f3476`](https://github.com/p1neappleXpress/OpenFlux/commit/f8f34767a5732febd5965ac6cf95bad51b70cf99).
  - A classic profile with a key runs the Session and falls back to the
    classic layering for a classic or older node, on the same carrier; a
    node set up as classic serves both kinds of client. Nodes set up for
    the Session stay Session-only.
  - The encryption context follows one rule everywhere, and a client whose
    context differs from the node's finds the node's instead of timing out
    (classic cups.online was the common case).
  - The classic codec (batched or legacy) is no longer a hard requirement:
    both are accepted and the client switches when the node does not answer.
  - boards no longer drops the connection every 20 seconds; yandex and
    mailru reconnect when their socket dies.
  - The log explains a failed handshake: wrong key, a node in the other
    mode, the codec or context picked, a connection taken over by another
    client.

## [2.0.1] - 2026-09-27

### Fixed

- An exit node deployed by the node wizard never actually connected: a
  `.conf`-only `Role = exit` (every node-wizard deployment) left the core's
  internal exit/client flag stuck at its pre-config value, so the exit
  never answered the handshake and crash-looped instead. Bumps `OpenFlux`
  to [`e8f735a`](https://github.com/p1neappleXpress/OpenFlux/commit/e8f735a98c1ba9e091956416fde5c5ef92d3cd66).
- The startup log always printed `Transport: yandex` for session/multi-transport
  profiles regardless of which transports were actually configured (a stale
  flag default, not a functional bug — the correct transports ran either
  way). Now prints the actual list, e.g. `Transport: boards, direct (session)`.
- Cups.online profiles with no room codes couldn't be saved or connected;
  the node generates its own rooms, so an empty value is valid for this
  transport only.
- An exit node always listened for Direct (TCP on `0.0.0.0:<port>`) and put
  it into the clients' link, even when the profile had no Direct transport.
  It now listens only when the profile has Direct, at that transport's
  priority ([#11](https://github.com/p1neappleXpress/OpenFluxAndroid/pull/11)).
- A phone exit on cups.online without room codes put cups.online into its
  link with no rooms, so clients could not join; the link now carries the
  rooms the node created ([OpenFlux#123](https://github.com/p1neappleXpress/OpenFlux/pull/123)).

### Added

- Settings → Ядро OpenFlux: a core log-level picker (Выкл / -d / -dd /
  -ddd, the core's `--debug=N`) in place of the "Подробный журнал ядра"
  switch. The embedded core used to run at -dd on every connect whatever
  the switch said — formatting a log line for every packet — and the
  switch only hid those lines from the log view; the level now reaches the
  core itself ([OpenFlux#121](https://github.com/p1neappleXpress/OpenFlux/pull/121)).
  The default is Выкл: choose -dd to see transport errors, handshakes and
  the encryption (KDF) context.
- Node-wizard deployment logging: every SSH/RPC call and the wizard's own
  step narration now goes to the Logs tab, so a stuck deployment is
  diagnosable without a debugger.
- Home → Подключение: a "Сейчас через" row for profiles with several
  transports, and carriers named as in the app ("Board 2", not `boards-2`)
  there and in the "через …" badge.

## [2.0.0] - 2026-09-27

First release of this app. Replaces the previous single-transport native
app (tun2socks + pdnsd JNI, preserved at the
[`legacy-native-app`](../../tree/legacy-native-app) tag) with the Compose
Multiplatform app originally built by [@meepo161](https://github.com/meepo161)
in [OpenFluxClient](https://github.com/meepo161/OpenFluxClient), moved here
with his agreement.

### Added

- System VPN or local SOCKS5.
- Multi-transport sessions with automatic failover and priority-based
  switching (including `direct`).
- AES-256-GCM session encryption.
- SmartCaptcha and login handling in a built-in browser, including a
  transport check on the exit node, passed through the tunnel from its
  own address.
- A node-deployment wizard: install an exit node on your own VPS over SSH
  from the app.
- `shared/` and `OpenFlux/` as git submodules ([OpenFluxClientShared](https://github.com/p1neappleXpress/OpenFluxClientShared)
  and the [OpenFlux](https://github.com/p1neappleXpress/OpenFlux) core), so
  this app always builds against one pinned, single copy of each instead of
  a vendored one.
- `.github/workflows/release.yml`: a `v*` tag builds and signs a release
  APK and publishes it here.

### Credits

- [@meepo161](https://github.com/meepo161) — this app's UI and logic.
- [@p1neappleXpress](https://github.com/p1neappleXpress) — the OpenFlux
  core it embeds.
- [@damnurmum](https://github.com/damnurmum) — `openflux://` links/QR codes
  and the cups.online transport in the core, which this app's share and
  scan screens build on.

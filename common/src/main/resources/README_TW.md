<div align="center">
  <p>
    <img src="https://cdn.modrinth.com/data/OA8LKtim/5ea92cfbacd6ab8f4a0e546d1589f0f95617d19b.webp">
    <h1>banco</h1>
    <a href="https://github.com/myth-MC/banco/releases/latest"><img src="https://img.shields.io/github/v/release/myth-MC/banco" alt="Latest release" /></a>
    <a href="https://github.com/myth-MC/banco/pulls"><img src="https://img.shields.io/github/issues-pr/myth-MC/banco" alt="Pull requests" /></a>
    <a href="https://github.com/myth-MC/banco/issues"><img src="https://img.shields.io/github/issues/myth-MC/banco" alt="Issues" /></a>
    <a href="https://github.com/myth-MC/banco/blob/main/LICENSE"><img src="https://img.shields.io/badge/license-GPL--3.0-blue.svg" alt="License" /></a>
    <br>
    一個簡單的基於物品的經濟插件
  </p>
</div>

<details open="open">
  <summary>快速閱覽</summary>
  <ol>
    <li>
      <a href="#information">簡介</a>
    </li>
    <li>
      <a href="#installation">安裝方法</a>
    </li>
    <li>
      <a href="#usage">使用方式</a>
    </li>
  </ol>
</details>

<div id="information"></div>

## 📚 簡介

**banco 🏦** 為伺服器服主提供了一個簡單且可配置的物品的貨幣系統，非常適合 RPG 風格的伺服器。

>[!警告]
> 本插件仍在開發中。即使它的大部分功能都可以使用，但我們不能保證沒有錯誤的機率。您可以透過 [creating an issue](https://github.com/myth-MC/banco/issues)來回報任何錯誤訊息或分享任何回饋。. 

### 特徵

* 基於**物品** 的經濟系統
* 可自訂的項目（顯示名稱、 (顯示名稱、指令重新載入 **自訂模型數據**)
* 支持使用[Vault](https://www.spigotmc.org/resources/vault.34315/)（Towny Advanced、Factions、Jobs Reborn...）
* 支持[PlaceholderAPI](https://www.spigotmc.org/resources/placeholderapi.6245/) 
* 支持**多種語言** (完整列表 [請見此處](https://docs.mythmc.ovh/banco/administration/translations))
* *輕量量** 且高效能
* 開發時考慮到**高自訂內容**和**簡單性**
* 除 Vault 之外沒有任何依賴項

### 計畫

* ~~[Folia](https://papermc.io/software/folia) 支援~~ ✅ (0.3+)
* ~~指令~~ ✅ (0.2+)
* ~~自訂模型和數據~~ ✅ (0.5+)
* 支援 MySQL
* ~~更新追衝~~ ✅ (0.2+)
* Baltop
* ~~計算中界箱內的物品~~ ✅ (0.4+)

### 相容性圖表

|                                                         | 是否兼容     | 支援版本| 備註                                          
|---------------------------------------------------------|-------------|---------|
| [PaperMC](https://papermc.io/)                          | ✅          | 1.20.6+ |                                              
| [PurpurMC](https://purpurmc.org/)                       | ✅          | 1.20.6+ |                                              
| [Spigot](https://www.spigotmc.org)                      | ✅          | 1.20+   | 考慮使用 [PaperMC](https://papermc.io)       
| [Bukkit](https://bukkit.org)                            | ✅          | 1.20+   | 考慮使用 [PaperMC](https://papermc.io) 
| [Folia](https://papermc.io/software/folia)              | ✅          | 1.20.6+ |                                              

### 依賴

* **必要** [Vault](https://www.spigotmc.org/resources/vault.34315/) 1.7+ 或 [VaultUnlocked](https://www.spigotmc.org/resources/vaultunlocked.117277/) 2.2+ 
* **選用** [PlaceholderAPI](https://www.spigotmc.org/resources/placeholderapi.6245/) (optional)
* **選用** [Towny Advanced](https://townyadvanced.github.io) (optional)

<div id="installation"></div>

## 📥 安裝方法

1. **下載適合您伺服器平台的 banco.jar 檔案**. 您可以在 [我們的發面](https://github.com/myth-MC/banco/releases)上找到最新版本。.
2. **將 banco jar 檔案新增至伺服器的插件資料夾**. 確保刪除所有舊版本的 banco。
3. **下載並安裝 [Vault](https://www.spigotmc.org/resources/vault.34315/)**.
4. **完全重新啟動您的伺服器**，輸入 `/stop` 並啟動伺服器 [而不是使用 `/reload`](https://madelinemiller.dev/blog/problem-with-reload/).

<div id="usage"></div>

## 🖊️ 用法

當您第一次執行 banco 時，它會自動產生兩個檔案：
* 'settings.yml' 包含常規設定
* 'accounts.yml' 儲存帳戶資料的地方

banco 配備了非常簡單的基於黃金的經濟設置，可以透過修改來擴展 `settings.yml`

#### 其他支援的插件

* [Towny Advanced](https://townyadvanced.github.io)
* [PlaceholderAPI](https://www.spigotmc.org/resources/placeholderapi.6245/)
* 任何其他支援 Vault 的插件
  
<div id="bugs"></div>

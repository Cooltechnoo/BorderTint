# 🩸 Border Tint

A lightweight, highly customizable client-side Fabric mod that adds a smooth, dynamic screen-edge tint when your health drops too low. Never lose track of your health during intense fights again.

![Minecraft version](https://img.shields.io/badge/Minecraft-1.21.11-blue?style=for-the-badge&logo=minecraft)
![Loader](https://img.shields.io/badge/Loader-Fabric-orange?style=for-the-badge)
![API](https://img.shields.io/badge/Config-YACL3-green?style=for-the-badge)

---

## ✨ Features

* **Dynamic Scaling:** The lower your health drops, the more intense the border tint becomes.
* **Fully Customizable Color:** Don't want red? Change the border to any custom RGB color you want.
* **Smooth Gradient Rendering:** Uses layered rendering to create a smooth, clean blend from the edges inward instead of a harsh blocky rectangle.
* **In-Game Configuration:** Seamless integration with **Mod Menu** and **Yet Another Config Lib (YACL)** for real-time adjustments without restarting the game.
* **Client-Side Only:** Works completely on your end—no server installation required!

---

## ⚙️ Configuration Options

You can adjust everything in real-time through the Mod Menu interface:

| Option | Default | Description |
| :--- | :--- | :--- |
| **Enable Screen Tint** | `true` | Quickly toggle the low-health overlay effect on/off. |
| **Heart Threshold** | `10.0` (5 Hearts) | The exact HP level where the tint begins to show (1 heart = 2 HP). |
| **Maximum Opacity** | `0.45` | Controls how dark/visible the overlay gets at its maximum intensity. |
| **Border Color** | `#FF0000` | Full color-picker support to change the tint to any color you like. |
| **Border Size (Pixels)**| `40` | Adjusts how far inward from the edge of your screen the gradient extends. |

---

## 🛠️ Requirements

To use this mod, ensure you have the following installed:
* [Fabric Loader](https://fabricmc.net/) (1.21.11)
* [Fabric API](https://modrinth.com/mod/fabric-api)
* [Yet Another Config Lib (YACL)](https://modrinth.com/mod/yacl) *(Required for the config menu)*
* [Mod Menu](https://modrinth.com/mod/modmenu) *(Optional, but recommended to access the settings UI)*

---

## 📝 License

This project is licensed under the MIT License - feel free to use it in your modpacks!

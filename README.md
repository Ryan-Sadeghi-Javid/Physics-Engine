# Java Physics Engine 🎮

A 2D platformer and physics simulation engine built from scratch in Java and JavaFX. This project simulates rigid body dynamics, collisions, and motion using object-oriented programming, modular design, and interactive GUI elements.

## 🚀 Features

- ✅ Real-time physics simulation
- ✅ Sprite-based character and enemy movement
- ✅ Collision detection and response
- ✅ JavaFX GUI rendering
- ✅ Modular architecture
- ✅ Editable levels

## 📸 Screenshots

![Gameplay Screenshot](./preview.png)

## 🧪 How to Run

### ✅ Prerequisites

- Java JDK 17 or newer
- JavaFX SDK 21 (download here: https://gluonhq.com/products/javafx/)

### 🏃 Run the Game

1. Download or clone the repository
2. Make sure `Mario.jar` and `LevelSelection.txt` are in the root
3. Edit the `run.bat` file and update the JavaFX path:

```bat
@echo off
set FX="C:\path\to\javafx-sdk-21\lib"
java --module-path %FX% --add-modules javafx.controls,javafx.fxml,javafx.media -jar Mario.jar
pause

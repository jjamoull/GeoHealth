![License](https://img.shields.io/badge/license-MIT-blue)
![Status](https://img.shields.io/badge/status-active-success)

![Java](https://img.shields.io/badge/Java-17-orange?logo=openjdk)
![Java](https://img.shields.io/badge/Java-22-orange?logo=openjdk)
![Maven](https://img.shields.io/badge/Maven-3.9-C71A36?logo=apachemaven)
![Spring Boot](https://img.shields.io/badge/SpringBoot-3.x-6DB33F?logo=springboot)

![Angular](https://img.shields.io/badge/Angular-22-DD0031?logo=angular)
![Node.js](https://img.shields.io/badge/Node.js-22.14.0-339933?logo=node.js)
![Angular](https://img.shields.io/badge/Angular_Core-21.1.2-DD0031?logo=angular)
![Angular CLI](https://img.shields.io/badge/Angular_CLI-21.1.x-DD0031?logo=angular)
![TypeScript](https://img.shields.io/badge/TypeScript-5.x-3178C6?logo=typescript)


![Docker](https://img.shields.io/badge/Docker-containerized-2496ED?logo=docker)

# GeoHealth
GeoHealth is an interactive and scalable tool for viewing, validating, and updating infectious disease 
risk maps. It serves as a webGIS application to enable policymakers and experts to validate and use risk maps effectively.
As for now, **Cameroon** is the only country covered by GeoHealth and the risk maps limit themselves to Ebola and Covid.
GeoHealth can be further extended to other countries or diseases if considered useful for the purpose it serves.


GeoHealth offers an intuitive, secure, and collaborative interface that is 
multilingual (FR/EN) and accessible via any browser (e.g.: Safari, Google Chrome, etc.).

## Table of Contents
- [Architecture](#architecture)
- [Getting Started](#getting-started)
- [Configuration](#configuration)
- [Running the project](#running-the-project)
- [Testing](#testing)
- [Documentation](#documentation)
- [Contributing](#contributing)

## Architecture

The service follows a layered architecture:


| **_Layer_**     |      **_Technology_**      | **_Role_** | 
|:----------|:--------------------:|:----:| 
| Backend          |  Java / Spring Boot  |   REST API, business logic   | 
| Database|           PostgreSQL           |   Data storage   |
| Frontend          |  TypeScript / Angular  |   User interface   | 
| CI/CD|           GitHub Actions           |   Automated build, test and integration   |

To obtain more information about the architecture : [GeoHealth - Technical Documentation](https://github.com/jjamoull/GeoHealth/tree/main/docs)

## Getting Started

### Prerequisites to use it

#### In general
| Software |  Group Version |  Recommended Version| Documentation|
|:----------|:--------------------:|:----:| :----:|
|  Java | 22.0.2 | Stable versions between 17 and 22 |Link to docs|
| Maven | 3.9.9 |3.9.x | Link to docs|
|  Docker Desktop |  / |  Latest stable | Link to docs|
|  IntelliJ IDEA |2025.3.2 |  Latest stable |  Link to docs|
| GitHub Desktop (optional)|  / | Latest stable |Link to docs| 

#### Concerning angular 
|       Group Version        |  Recommended Version| Documentation|
|:--------------------------:|:----:| :----:|
|        Core: 21.1.2        | 21.1.x|Link to docs|
|      Node.js: 22.14.0      |Latest compatible | Link to docs|
|              Git 2.46.2              |  Latest compatible | Link to docs|

To obtain more information about the prerequisites : [GeoHealth - Technical Documentation](https://github.com/jjamoull/GeoHealth/tree/main/docs)

## Configuration

### 1) Clone the repository
You need to clone the repository by typing the following command line :\
`git clone https://github.com/jjamoull/GeoHealth.git` in your **terminal**  <br>**or** 
directly via Github CLI : `gh repo clone jjamoull/GeoHealth`

### 2) Create an environment file 
You have to create a new environment (.env) file at the root of the project.
In this file you have to add all these variables (for example) : <br>
`DB_USER= test `<br>
`DB_PASSWORD= test`<br>
`JWT_SECRET= test`<br>
`SUPERADMIN_SECRET= test`

### 3) Setting up docker 
Execute this command in your terminal at the root of the project:<br>
`docker compose up -d`

To obtain more information about the configuration : [GeoHealth - Technical Documentation](https://github.com/jjamoull/GeoHealth/tree/main/docs)

### 4) Launching your backend
Thanks to intelliJ you can configure your backend, and you have to follow those steps : 
- Click on the **three dots** in the top right corner of your editor.
- Click on the button : `Edit…` from subsection _Configurations_ <br>

If everything works as intended, a new window opens on your computer. Now :
- Click on the button `+` and select `Spring boot` with a leaf as the logo
- Select `Java 17` and for main class : `com.webgis.WebgisApplication`
- Click on the button : `Modify options` and select `Environment variables`

A new subsection called `Environment variables` appeared on your window, now you 
have to select the environment file that you have created prior to this step. 
- Once you have done this task you can click on `Apply` and after `OK`

You are now ready to run the backend. (Don't forget to execute the step [3) Setting up docker](#3-setting-up-docker-) 
every time before start your backend)

### 5) Launching your frontend

If you do have npm installed already, you can skip to the package installation

To install npm you will need Node on your computer. For this you have those options depending on your OS.

#### MacOS
On your terminal : brew install node
Or go on : https://nodejs.org/


#### Linux
On your terminal :  sudo apt update
sudo apt install nodejs npm


#### Windows
https://nodejs.org/

#### After you have to verify if everything’s is correctly installed by using those commands on your terminal :
node -v
npm -v


#### Package installation
Next, all you have to do is install the needed packages using :<br>
`cd frontend`<br>
`npm install`


#### Via terminal
Now you can run this command to launch the server :<br>` ng serve`<br>

#### Via IntelliJ
But you can also run thanks to IntelliJ with those steps : <br>
- Click on the **three dots** in the top right corner of your editor.
- Click on the `Edit…` button in subsection _Configurations_ <br>

Normally a new window opened on your computer. Now :
- Click on the `+` button and select `npm` with a red and white logo
- package.json : `~/Documents/GitHub/GeoHealth/frontend/package.json`
- Command : `run`
- Scripts : `Start` 
- Once you have done those steps, click on `Apply` and after `OK`

## Running the project

### For backend 
See [4) Launching your backend](#4-launching-your-backend) <br>
Or you can run this command to launch the server :<br>
`mvn exec:java -Dexec.mainClass="com.webgis.WebgisApplication"`

### For frontend 
See [5) Launching your frontend](#5-launching-your-frontend) <br>
Or you can run this command to launch the server :<br>` ng serve`<br>

## Testing 
To run the testing suite of this project you can run the tests in backend and/or in frontend

### About backend 
Execute this command : <br> `mvn clean test`<br>

### About frontend
Execute this command : <br> `npm test`<br>

## Documentation
A complete pdf has been created to explain you every aspect of the project in more details. You can find it 
in section /docs at the root of the project. You can follow this link : [GeoHealth - Technical Documentation](https://github.com/jjamoull/GeoHealth/tree/main/docs)

## Contributing
Please read [CONTRIBUTING.md](https://github.com/jjamoull/GeoHealth/blob/main/docs/CONTRIBUTOR.md) 
before submitting a pull request.
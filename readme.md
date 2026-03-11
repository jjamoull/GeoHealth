# GeoHealth
Interactive and scalable tool for viewing, validating, and updating infectious disease 
risk maps. It is an WebGIS based on Ebola and Rift Valley Fever risk maps 
in **Cameroon** in order to enable policymakers and experts to validate and use them 
effectively. 

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
You need to clone the repository thanks to :\
`git clone https://github.com/jjamoull/GeoHealth.git` in your **terminal**  <br>**or** 
directly via Github CLI : `gh repo clone jjamoull/GeoHealth`

### 2) Create an environment file 
You need to create a new environment file at the root of the project.
In this file you have to add all these variables (for example) : <br>
`DB_USER= test `<br>
`DB_PASSWORD= test`<br>
`JWT_SECRET= test`<br>
`SUPERADMIN_SECRET= test`

### 3) Establish the docker 
Execute this command in your terminal at the root of the project:<br>
`docker compose up -d`

To obtain more information about the configuration : [GeoHealth - Technical Documentation](https://github.com/jjamoull/GeoHealth/tree/main/docs)

### 4) Launching your backend
Thanks to intelliJ you can configure your backend, and you have to follow those steps : 
- Click on the **three dots** in the top right corner of your editor.
- Click on the button : `Edit…` from subsection _Configurations_ <br>

Normally a new window opened on your computer. Now :
- Click on the button `+` and select `Spring boot` with a leaf for the logo
- Select `Java 17` and for main class : `com.webgis.WebgisApplication`
- Click on the button : `Modify options` and select `Environment variables`

A new subsection opened `Environment variables` appeared on your window and now you 
have to select the environment file that you have created in the past. 
- Once you have done this task you can click on `Apply` and after `OK`

You are now ready to run the backend. (Don't forget to execute the step [3) Establish the docker](#3-establish-the-docker-) 
every time before start your backend)

### 5) Launching your frontend
If **_npm_** is not already install on your computer you can run this command in your terminal in section frontend:<br>
`cd frontend`<br>
`npm install`

#### Via terminal
Now you can run this command to launch the server :<br>` ng serve`<br>

#### Via IntelliJ
But you can also run thanks to IntelliJ with those step : <br>
- Click on the **three dots** in the top right corner of your editor.
- Click on the button : `Edit…` from subsection _Configurations_ <br>

Normally a new window opened on your computer. Now :
- Click on the button `+` and select `npm` with a red and white logo
- package.json : `~/Documents/GitHub/GeoHealth/frontend/package.json`
- Command : `run`
- Scripts : `Start` 
- Once you have done those task you can click on `Apply` and after `OK`

## Running the project

### For backend 
See [4) Launching your backend](#4-launching-your-backend) <br>
Or you can run this command to launch the server :<br>
`mvn exec:java -Dexec.mainClass="com.webgis.WebgisApplication"`

### For frontend 
See [5) Launching your frontend](#5-launching-your-frontend) <br>
Or you can run this command to launch the server :<br>` ng serve`<br>

## Testing 
To run the test on this project you can run the test in backend and/or in frontend

### About backend 
Execute this command : <br> `mvn clean test`<br>

### About frontend
Execute this command : <br> `npm test`<br>

## Documentation
A complete pdf has been created to explain you every aspect of the project and this one 
is in section /docs at the root of the project. You can follow this link : [GeoHealth - Technical Documentation](https://github.com/jjamoull/GeoHealth/tree/main/docs)

## Contributing
Please read [CONTRIBUTING.md](https://github.com/jjamoull/GeoHealth/blob/main/docs/CONTRIBUTOR.md) 
before submitting a pull request.
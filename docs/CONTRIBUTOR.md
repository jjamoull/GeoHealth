# Contribute to this project

## 1 How to contribute 
To contribute to the Geahealth project, new collaborators that join the project are people that gets added by the administrator. They can either code, review or tests. Please refer to the dedicated sections for each elements to have complete information. In addition, to add new features you have to create a new branch, if you don't know how to start refer to Branch management policy section.

## 2 Branch management policy

### 2.1 General explanation 
To handle all branch, we will follow the trunk base development policy. The idea is to have only one master branch (the "main" branch in our case) and to create new branches for each new features by forking the "main" branch. All branches must only be use to implement one feature and shouldn't live longer than a couple of days. To ensure that merges dont break the build, all branches are automatically analyse by sonar cube and tested automatically when doing a pull request. 

So, if you want to add new features, the first step will be to create a new branch based on the "main" branch, please refer to the "Branch name policy" to name your branch correctly. When it is done you can start coding your features and test them locally. Whenever your a satisfied with your work, make a pull request on the "main" branch by refering to the "Pull Request Convention" section then you just need to wait that a reviewer valid or not your pull request. Note that if the sonar cube or automated test fail, your code won't be reviewed.

### 2.2 Branch name policy
To name the branch correctly, here is the template you must follow for each type of contribution :

For features (new functionnality) :
feature/name-of-the-feature

For bugfix :
bugfix/name-of-the-fixed-problem

For documents :
doc/part-of-documentation-updated

In general, make sure you have only lower case and Hyphens-separeted word, use only one hyphen and do not put it at the end of the name. Use only alphanumeric character and try to make as descriptive and concive name as possible.


### 2.3 Where does a branch shoud be placed 
You should clone "main" and so, the branch is  
### 2.4 When a branch can be merged
You can only merge your branch with ‘main’ after your pull request has been validated by the other contributors.
## 3 Commit conventions
To commit your changes correctly, here is the general template you must follow for each commit, it must be written in English :

Summary : 
<type> : Short description of your changes

Description : 
Changed file : The file(s) that you changed
Detailed description of what you did

Type : 
- feat : Commits that add, adjust or remove a new feature to the API or UI
- fix : Commits that fix an API or UI bug of a preceded feat commit
- style : Commits that address code style (e.g., white-space, formatting, missing semi-colons) and do not affect application behavior
- test : Commits that add missing tests or correct existing ones
- docs : Commits that exclusively affect documentation
- build : Commits that affect build-related components such as build tools, dependencies, project version, CI/CD pipelines,...
- ops : Commits that affect operational components like infrastructure, deployment, backup, recovery procedures, ...
- chore : Commits that represent tasks like initial commit, modifying .gitignore, ...


## 4 Review conventions 

## 5 Code conventions to respect

## 6 Dummy issues explanations

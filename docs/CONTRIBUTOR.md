# Contribute to this project

## 1.What is this document ?

If you are here, it means that you want to contribute to this project (thank you!).

In this document, you will find all the guidelines you must follow to contribute correctly to the repository. The following sections are covered:

- **Branch Management Policy**: explains how you should manage your branches
- **Commit Policy**: explains which commit message standards you should follow
- **Review Template**: explains which template you should use when performing a review
- **Code Conventions**: explains the conventions you should follow when writing code

Note that we try to be as exhaustive as possible, but it is not possible to define rules for every situation. If no rule applies to a specific case, please look at how other contributors work and follow general best practices.

## 2 Branch management policy


## 2.1 General Explanation

To handle all branches, we will follow the **trunk-based development policy**. The idea is to have only one master branch (the "main" branch in our case) and to create new branches for each new feature by forking the "main" branch.

All branches must be used **only to implement one feature** and **shouldn't live longer than a couple of days**. To ensure that merges do not break the build, all branches are automatically analyzed by *SonarQube*, *Checkstyle*, and tested automatically when creating a pull request.

Here are the main steps you should follow when you want to add a new feature:

1. **Create a new branch** by forking the "main" branch. Please refer to the **Branch Name Policy** to name your branch correctly.
2. **Start coding your feature** on your branch. Don’t forget to commit your code and test it regularly.
3. Whenever you are satisfied with your work, **create a pull request** on the "main" branch.
4. **Wait for a reviewer** to validate your pull request.  
   *(Note: if SonarQube, Checkstyle, or automated tests fail, your code won't be reviewed.)*
5. You will either have to **make the changes requested by the reviewer**, or your code will be merged.


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
You want your branch to be merged but you don’t know what the necessary steps are. Here is the checklist:

- All automated tests have passed
- Checkstyle has passed
- The SonarQube analysis shows that the coverage is higher than 70%
- All changes requested by the reviewer have been made (or at least a decision has been taken if the change asked was not relevant)
- At least one reviewer other than you has approved your branch

If any of these steps is not completed, your branch won’t be merged.
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

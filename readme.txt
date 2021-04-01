--------------
SMARTY PROJECT
--------------

REPO WORKFLOW

# set a local clone of master in shared repo
mkdir REPOS
cd REPOS
git clone -b master https://github.com/astrion/Concordia_COMP5541_Winter2021.git
cd Concordia_COMP5541_Winter2021

# get the latest branch info and check branches
git fetch
git branch
git branch -a
git status

# create a new branch
git branch my_branch
git checkout my_branch
git checkout -b poc_YourInitials

# set local branch to pull upstream from local master
git pull
git branch --set-upstream-to=master poc_YourInitials

# work on your branch, and then check diff and status
git diff
git status

# commit your changes
git add src/file1
git add src/file2
git commit -m "#: update"
git status

# push
git push -u origin poc_YourInitials

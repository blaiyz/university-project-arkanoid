# university-project-arkanoid
This is the final result of the project given as homework at my university. It is a simple game of Arkanoid with 3 levels and (epic) animations (that were definitely required to submit and not just something I wasted my time on)

![preview](https://github.com/blaiyz/university-project-arkanoid/assets/139375534/3f017084-04a5-4a7b-af49-771724b3b41d)

# How to run
The project comes with an ant config file (`build.xml`).
Install ant here: https://ant.apache.org/manual/install.html

Run `ant compile` in order to compile the project

Run `ant run` in order to run the project with all the levels.

Run `ant run -Dargs="x y z w ..."` in order to run the chosen levels in succession, where x, y, ... represent the indexes of the levels (starts with 1 and supports multiple of the same level). The normal `ant run` is equivalent to `ant run -Dargs="1 2 3"`.

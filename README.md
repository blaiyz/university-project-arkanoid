# university-project-arkanoid
This is the final result of the project given as homework at my university. It is a simple game of Arkanoid with 3 levels and (epic) animations (that were definitely required to submit and not just something I wasted my time on)

![preview](https://github.com/blaiyz/university-project-arkanoid/assets/139375534/8a1fa958-5490-4f9c-8cbd-ea45b8faab09)

# How to run
The project comes with an ant config file (`build.xml`).
Install ant here: https://ant.apache.org/manual/install.html

Use `ant compile` in order to compile the project
Use `ant run` in order to run the project with all the levels.
Use `ant run -Dargs="x y z w ..."` in order to run the chosen levels in succession, where x, y, ... represent the indexes of the levels (starts with 1 and supports multiple of the same level). The normal `ant run` is equivalent to `ant run -Dargs="1 2 3"`.

# Algorithms

Java based project as completing of assignments on algorithms and data structures, with an emphasis on applications and scientific performance analysis of Java implementations:

* [Coursera, Algorithms Part I](https://www.coursera.org/learn/algorithms-part1): all assignments (score: 92.4%)
* [Coursera, Algorithms Part II](https://www.coursera.org/learn/algorithms-part2): all assignments (score: 95%)
* Robert Lafore, Structure of data & Algorithms:  code and assignments


# Pre-setup

### Setup algs4 repository from Github
`algs4` repository out of central Maven repository. So build and add `algs4` artifact to your local Maven repository.

#### Clone algs4 repository (here to ../_others/out-of-maven/algs4)
```shell
git clone https://github.com/kevin-wayne/algs4.git \
../_others/out-of-maven/algs4
```
#### Install repository to get .jar file
```shell
mvn clean install \ 
-f ../_others/out-of-maven/algs4/pom.xml
```

#### Install .jar file as artifact into your local Maven repository
```shell
mvn install:install-file \
-Dfile=../_others/out-of-maven/algs4/target/algs4-1.0.0.0.jar \
-DgroupId=edu.princeton.cs.algs4 \
-DartifactId=algs4 \
-Dversion=1.0.0 \
-Dpackaging=jar
```
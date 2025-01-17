# Algorithms:
* Coursera, Algorithms Part I: all assignments:     Score: 92.4%
* Coursera, Algorithms Part II: all assignments:    Score: 95%
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
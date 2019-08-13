# About
This project uploads a RDF file into a specified SPARQL Repository endpoint. It is possible to optionally define username and password. 

* Faster on [RDF4J servers](https://rdf4j.eclipse.org/documentation/server-workbench-console/), as we use RDF4J. Mainly tested on [GraphDB](https://www.ontotext.com/products/graphdb/graphdb-free/).  

* This Docker container is part of the [LODQuA pipeline](https://github.com/MaastrichtU-IDS/dqa-pipeline/).

# Docker
## Build
```shell
docker build -t rdf-upload .
```
## Usage
```shell
docker run -it --rm rdf-upload -?

Usage: rdfupload [-?] [-ep=<endpoint>] -if=<inputFile> [-pw=<passWord>]
                 -rep=<repository> [-uep=<updateEndpoint>] [-un=<userName>]
                 -url=<url>
  -?, --help   display a help message
      -if, --inputFile=<inputFile>
               RDF file path
      -pw, --Password=<passWord>
               Password used for authentication
      -rep, --repository=<repository>
               Repository ID
      -un, --userName=<userName>
               Username userd for authentication
      -url, --graphdb-url=<url>
               URL to access GraphDB (e.g.: http://localhost:7200)

```
## Run
- Linux / OSX

```shell
# RDF4J server URL + repository ID
docker run -it --rm -v /data/rdfu:/data rdf-upload -if "/data/rdf_output.ttl" -url "http://localhost:7200" -rep "test" -un USERNAME -pw PASSWORD

# With full SPARQL endpoint URL
docker run -it --rm -v /data/rdfu:/data rdf-upload -if "/data/rdf_output.ttl" -url "http://localhost:7200/repositories/test" -un USERNAME -pw PASSWORD
```

- Windows

```powershell
docker run -it --rm -v c:/data/rdfu:/data rdf-upload -if "/data/rdf_output.ttl" -url "http://localhost:7200" -rep "test" -un USERNAME -pw PASSWORD
```



# Preload

```shell
docker run -d --rm --name graphdb -p 7200:7200 \
  -v /data/graphdb:/opt/graphdb/home \
  -v /data/graphdb-import:/root/graphdb-import \
  graphdb
```



Issue: GraphDB needs to be stopped when running the load tool. Killing the java process stop the container

Try without the `--rm` flag

```shell
/opt/graphdb/dist/bin/preload -f -i <repo-name> <RDF data file(s)>

preload -f -i test /data/graphdb-preload/biogrid_dataset.ttl


docker run -it -v /data/graphdb:/opt/graphdb/home -v /data/graphdb-import:/root/graphdb-import --entrypoint "/opt/graphdb/dist/bin/preload -f -i test /opt/graphdb/home/data/rdf_output.nq" graphdb

# Not failing, but nothing load when docker start graphdb
docker run -it -v /data/graphdb:/opt/graphdb/home -v /data/graphdb-import:/root/graphdb-import --entrypoint /opt/graphdb/dist/bin/preload graphdb -c /root/graphdb-import/repo-config.ttl /opt/graphdb/home/data/rdf_output.nq

# Create repo test using config file. Works but GraphDB should not run
docker exec -it graphdb /opt/graphdb/dist/bin/preload -c "/root/graphdb-import/repo-config.ttl" "/opt/graphdb/home/data/rdf_output.nq"
# Use existing test repo
docker exec -it graphdb /opt/graphdb/dist/bin/preload -f -i test "/opt/graphdb/home/data/rdf_output.nq"
```

repo-config.ttl:

```SPARQL
# Configuration template for an GraphDB-Free repository
@prefix rdfs: <http://www.w3.org/2000/01/rdf-schema#>.
@prefix rep: <http://www.openrdf.org/config/repository#>.
@prefix sr: <http://www.openrdf.org/config/repository/sail#>.
@prefix sail: <http://www.openrdf.org/config/sail#>.
@prefix owlim: <http://www.ontotext.com/trree/owlim#>.
[] a rep:Repository ;
    rep:repositoryID "test" ;
    rdfs:label "Test repo" ;
    rep:repositoryImpl [
        rep:repositoryType "graphdb:FreeSailRepository" ;
        sr:sailImpl [
            sail:sailType "graphdb:FreeSail" ;
            # ruleset to use
            owlim:ruleset "empty" ;
            # disable context index(because my data do not uses contexts)
            owlim:enable-context-index "true" ;
            # indexes to speed up the read queries
            owlim:enablePredicateList "true" ;
            owlim:enable-literal-index "true" ;
            owlim:in-memory-literal-properties "true" ;
        ]
    ].
```


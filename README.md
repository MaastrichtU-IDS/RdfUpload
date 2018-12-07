# About
This project uploads a RDF file into a specified GraphDB HTTP Repository endpoint. It is possible to optionally define username and password.

This Docker container is part of the LODQuA pipeline (https://github.com/MaastrichtU-IDS/dqa-pipeline/).

# Docker
## Build
```shell
docker build -t rdf-upload .
```
## Usage
```shell
# docker run -it --rm rdf-upload -?

Usage: rdfupload [-?] [-ep=<endpoint>] -if=<inputFile> [-pw=<passWord>]
                 -rep=<repository> [-uep=<updateEndpoint>] [-un=<userName>]
                 -url=<url>
  -?, --help   display a help message
      -ep, --endPoint=<endpoint>
               SPARQL endpoint URL
      -if, --inputFile=<inputFile>
               RDF file path
      -pw, --Password=<passWord>
               Password used for authentication
      -rep, --repository=<repository>
               Repository ID
      -uep, --updateEndPoint=<updateEndpoint>
               SPARQL udpate endpoint
      -un, --userName=<userName>
               Username userd for authentication
      -url, --graphdb-url=<url>
               URL to access GraphDB (e.g.: http://localhost:7200)

```
## Run
### For HTTPRepository

- Linux / OSX

```shell
docker run -it --rm -v /data/rdfu:/data rdf-upload -if "/data/rdf_output.ttl" -url "http://localhost:7200" -rep "kraken_test" -un admin -pw admin
```

- Windows

```powershell
docker run -it --rm -v c:/data/rdfu:/data rdf-upload -if "/data/rdf_output.ttl" -url "http://localhost:7200" -rep "kraken_test" -un admin -pw admin
```



### For SPARQLRepository (old version)

* Linux / OSX

```shell
docker run -it --rm -v /data/rdfu:/data rdf-upload -if "/data/rdffile.nt" -ep "http://localhost:7200/sparql"
```
* Windows

```powershell
docker run -it --rm -v /c/data/rdfu:/data rdf-upload -if "/data/rdffile.nt" -ep "http://localhost:7200/sparql"
```



# Preload

```shell
/opt/graphdb/dist/bin/preload -f -i <repo-name> <RDF data file(s)>

docker build -t preload .
docker run -it -v /data/graphdb-preload:/data preload -f -i test /data/biogrid_dataset.ttl 

# Almost working:
docker run -it -v /data/graphdb-preload:/data -v /data/graphdb:/opt/graphdb/home -v /data/graphdb-import:/root/graphdb-import preload -c "/data/repo-config.ttl" "/data/biogrid_dataset.ttl"
```

repo-config.ttl:

```python
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


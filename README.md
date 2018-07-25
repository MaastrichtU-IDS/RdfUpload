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

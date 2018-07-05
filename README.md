# About
This project uploads a RDF file into a specified SPARQL V1.1 endpoint. It is possible to optionally define an update endpoint, username and password.

This Docker container is part of the LODQuA pipeline (https://github.com/MaastrichtU-IDS/dqa_pipeline/).

# Docker
## Build
```
# docker build -t rdfupload .
```
## Usage
```
# docker run -it --rm rdfupload -?

Usage: rdfupload [-?] -ep=<endpoint> -if=<inputFile> [-pw=<passWord>]
                 -uep=<updateEndpoint> [-un=<userName>]
  -?, --help   display a help message
      -ep, --endPoint=<endpoint>
               SPARQL endpoint URL
      -if, --inputFile=<inputFile>
               RDF file path
      -pw, --Password=<passWord>
               Password used for authentication
      -uep, --updateEndPoint=<updateEndpoint>
               SPARQL udpate endpoint
      -un, --userName=<userName>
               Username userd for authentication

```
## Run
### Linux / OSX
```
# docker run -it --rm -v /data/rdfu:/data rdfupload -if "/data/rdffile.nt" -ep "http://myendpoint.org/sparql"
```
### Windows
```
# docker run -it --rm -v /c/data/rdfu:/data rdfupload -if "/data/rdffile.nt" -ep "http://myendpoint.org/sparql"
```

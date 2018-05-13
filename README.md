# About
This project uploads a RDF file into a specified SPARQL V1.1 endpoint. It is possible to optionally define an update endpoint, username and password.

This Docker container is part of the LODQuA pipeline (https://github.com/MaastrichtU-IDS/dqa_pipeline/).

# Docker
## Build
```
docker build -t rdf-upload .
```
## Usage
```
docker run -it --rm rdf-upload
```
## Run
### Linux / OSX
```
docker run -it --rm -v /data/rdfu:/data rdf-upload -if "/data/rdffile.nt" -ep "http://myendpoint.org/sparql"
```
### Windows
```
docker run -it --rm -v /c/data/rdfu:/data rdf-upload -if "/data/rdffile.nt" -ep "http://myendpoint.org/sparql"
```

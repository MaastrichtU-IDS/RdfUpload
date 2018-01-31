# About
This project uploads a RDF file into a SPARQL V1.1 endpoint. It is possible to optionally define an update endpoint, username and password.
# Docker
## Build
```
docker build -t rdf-upload .
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

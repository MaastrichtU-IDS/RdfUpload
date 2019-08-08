package nl.unimaas.ids;

import java.sql.SQLException;

import org.eclipse.rdf4j.repository.Repository;
import org.eclipse.rdf4j.repository.http.HTTPRepository;
import org.eclipse.rdf4j.repository.sparql.SPARQLRepository;

public class SparqlRepositoryFactory {
	
	// Try to load HTTPRepository, load SPARQLRepository if fail
	public static Repository getRepository(String endpointUrl, String repositoryId, String username, String password) throws SQLException, ClassNotFoundException {
		if (repositoryId != null) {
			// If RDF4J repository ID is provided we loaded using it instead of the repository URL
			// It allows to do select and update on same repo (with URL you have the repo and repo/statements for update)
			HTTPRepository httpRepo = new HTTPRepository(endpointUrl, repositoryId);
        	httpRepo.setUsernameAndPassword(username, password);
        	httpRepo.initialize();
        	return httpRepo;
		} else {
			if (endpointUrl.endsWith("/statements") && endpointUrl.contains("/repositories/")) {
				// If a URL to a RDF4J update repository with /statements is provided
				// Then we parse the URL to get a HTTP repo from triplestore URL + repo ID
				repositoryId = endpointUrl.substring(endpointUrl.indexOf("/repositories/") + 14, endpointUrl.indexOf("/statements"));
				endpointUrl = endpointUrl.substring(0, endpointUrl.indexOf("/repositories/"));
				HTTPRepository httpRepo = new HTTPRepository(endpointUrl, repositoryId);
	        	httpRepo.setUsernameAndPassword(username, password);
	        	httpRepo.initialize();
	        	return httpRepo;
			} else {
				System.out.println("Triplestore is not a RDF4J server, using SPARQLRepository instead of HTTPRepository");
				SPARQLRepository sparqlRepo = new SPARQLRepository(endpointUrl);
	   	 		sparqlRepo.setUsernameAndPassword(username, password);
	   	 		sparqlRepo.initialize();
	   	 		return sparqlRepo;
			}
			//} catch (IllegalArgumentException e) {
		}
	} 
}
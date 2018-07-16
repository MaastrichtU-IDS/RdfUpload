package nl.unimaas.ids.loader;

import org.eclipse.rdf4j.repository.RepositoryConnection;
import org.eclipse.rdf4j.repository.http.HTTPRepository;
import org.eclipse.rdf4j.repository.sparql.SPARQLRepository;
import org.eclipse.rdf4j.rio.Rio;
import picocli.CommandLine;

import java.io.File;

public class SparqlLoader {

	public static void uploadRdf(boolean isRdf4jSparql, String filePath, String endpoint, String username, String password) throws Exception {

		SPARQLRepository repo;

		if(isRdf4jSparql == true) {
			repo = new SPARQLRepository(endpoint, endpoint + "/statements");
		} else {
			repo = new SPARQLRepository(endpoint);
		}

		repo.setUsernameAndPassword(username, password);
		repo.initialize();


		try (RepositoryConnection conn = repo.getConnection()) {
			File inputFile = new File(filePath);
			if(!inputFile.exists())
				throw new IllegalArgumentException("Input file \"" + inputFile.getAbsolutePath() + "\" does not exist");
			if(!inputFile.canRead())
				throw new SecurityException("Can not read from input file \"" + inputFile.getAbsolutePath() + "\"");

			conn.add(new File(filePath), null, Rio.getParserFormatForFileName(inputFile.getName()).get());
		} catch (Exception e) {
			throw e;
		}

		repo.shutDown();
	}
}

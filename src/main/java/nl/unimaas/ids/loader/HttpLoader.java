package nl.unimaas.ids.loader;

import org.eclipse.rdf4j.repository.RepositoryConnection;
import org.eclipse.rdf4j.repository.http.HTTPRepository;
import org.eclipse.rdf4j.rio.Rio;

import java.io.File;

/**
 * A class to upload to GraphDB HTTP endpoint
 */
public class HttpLoader {

	public static void uploadRdf(String filePath, String dbUrl, String repository, String username, String password) throws Exception {

		HTTPRepository repo;

		repo = new HTTPRepository(dbUrl, repository);

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

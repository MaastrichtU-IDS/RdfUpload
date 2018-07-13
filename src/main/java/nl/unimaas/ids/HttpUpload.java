package nl.unimaas.ids;

import org.eclipse.rdf4j.repository.RepositoryConnection;
import org.eclipse.rdf4j.repository.http.HTTPRepository;
import org.eclipse.rdf4j.rio.Rio;

import java.io.File;

/**
 * A class to upload to GraphDB HTTP endpoint
 */
public class  HttpUpload {

	public static void uploadRdf(String filePath, String databaseUrl, String repositoryId, String userName, String passWord) throws Exception {


		/*SPARQLRepository repo;
		if(cli.updateEndpoint!=null)
			repo = new SPARQLRepository(cli.endpoint, cli.updateEndpoint);
		else
		repo = new SPARQLRepository(cli.endpoint);*/

		// http://docs.rdf4j.org/programming/
		HTTPRepository repo;

		repo = new HTTPRepository(databaseUrl, repositoryId);

		repo.setUsernameAndPassword(userName, passWord);
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

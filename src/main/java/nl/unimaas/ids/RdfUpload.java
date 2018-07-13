package nl.unimaas.ids;

import java.io.File;

import org.eclipse.rdf4j.repository.RepositoryConnection;
import org.eclipse.rdf4j.repository.http.HTTPRepository;
import org.eclipse.rdf4j.repository.sparql.SPARQLRepository;
import org.eclipse.rdf4j.rio.Rio;

import picocli.CommandLine;

public class RdfUpload {

	public static void main(String[] args) throws Exception {
		try {
			
			CliOptions cli = CommandLine.populateCommand(new CliOptions(), args);
			if(cli.help) 
				printUsageAndExit();

			final String filePath = cli.inputFile;

			/*SPARQLRepository repo;
			if(cli.updateEndpoint!=null)
				repo = new SPARQLRepository(cli.endpoint, cli.updateEndpoint);
			else
			repo = new SPARQLRepository(cli.endpoint);*/

			HTTPRepository repo;

			repo = new HTTPRepository(cli.url, cli.repository);

			repo.setUsernameAndPassword(cli.userName, cli.passWord);
			repo.initialize();
			
			
			try (RepositoryConnection conn = repo.getConnection()) {
				File inputFile = new File(filePath);
				if(!inputFile.exists())
					throw new IllegalArgumentException("Input file \"" + inputFile.getAbsolutePath() + "\" does not exist");
				if(!inputFile.canRead())
					throw new SecurityException("Can not read from input file \"" + inputFile.getAbsolutePath() + "\"");
				
				conn.add(new File(filePath), null, Rio.getParserFormatForFileName(inputFile.getName()).get());
			} catch (Exception e) {
				printUsageAndExit(e);
			}
		
			repo.shutDown();
		} catch (Exception e) {
			printUsageAndExit(e);
		}
	}
	
	private static void printUsageAndExit() {
		printUsageAndExit(null);
	}
	
	private static void printUsageAndExit(Throwable e) {
		CommandLine.usage(new CliOptions(), System.out);
		if(e == null)
			System.exit(0);
		e.printStackTrace();
		System.exit(-1);
	}
}

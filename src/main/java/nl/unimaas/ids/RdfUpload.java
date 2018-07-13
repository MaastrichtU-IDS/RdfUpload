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

			if(cli.url !=null && cli.repository != null) {
				HttpUpload.uploadRdf(cli.inputFile, cli.url, cli.repository, cli.userName, cli.passWord);
			} else if (cli.endpoint !=null && cli.updateEndpoint != null) {
				SparqlUpload.uploadRdf(cli.inputFile, cli.endpoint, cli.updateEndpoint, cli.userName, cli.passWord);
			} else {
				// Print error message
			}

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

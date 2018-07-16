package nl.unimaas.ids;

import nl.unimaas.ids.loader.HttpLoader;
import nl.unimaas.ids.loader.SparqlLoader;

import picocli.CommandLine;

public class RdfUpload {

	public static void main(String[] args) throws Exception {
		try {
			
			CliOptions cli = CommandLine.populateCommand(new CliOptions(), args);
			if(cli.help)
				printUsageAndExit();



			if(cli.method.equals("RDF4JSPARQL")) {
				String endpoint = null;

				if (cli.repositoryId != null)
					// If the repository ID is provided then we built the GraphDB endpoint URL using it
					endpoint = cli.dbUrl + "/repositories/" + cli.repositoryId;
				else
					endpoint = cli.dbUrl;

				SparqlLoader.uploadRdf(true, cli.inputFile, endpoint, cli.username, cli.password);

			} else if (cli.method.equals("HTTP")) {

				HttpLoader.uploadRdf(cli.inputFile, cli.dbUrl, cli.repositoryId, cli.username, cli.password);

			} else if (cli.method.equals("SPARQL")) {

				SparqlLoader.uploadRdf(false, cli.inputFile, cli.dbUrl, cli.username, cli.password);

			} else {
				printUsageAndExit();
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

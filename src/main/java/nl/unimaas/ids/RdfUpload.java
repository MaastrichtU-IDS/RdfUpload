package nl.unimaas.ids;

import org.eclipse.rdf4j.repository.Repository;

import picocli.CommandLine;

public class RdfUpload {

	public static void main(String[] args) throws Exception {
		try {
			CliOptions cli = CommandLine.populateCommand(new CliOptions(), args);
			
			if(cli.help)
				printUsageAndExit();

			Repository repo = SparqlRepositoryFactory.getRepository(cli.dbUrl, cli.repositoryId, cli.username, cli.password);
			
			SparqlLoader.uploadRdf(cli.inputFile, repo);

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

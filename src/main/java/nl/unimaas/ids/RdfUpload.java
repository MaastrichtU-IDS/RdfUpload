package nl.unimaas.ids;

import java.io.File;

import org.eclipse.rdf4j.repository.Repository;
import org.apache.tools.ant.DirectoryScanner;

import picocli.CommandLine;

public class RdfUpload {
	
	public static void main(String[] args) throws Exception {
		try {
			CliOptions cli = CommandLine.populateCommand(new CliOptions(), args);
			
			if(cli.help)
				printUsageAndExit();

			Repository repo = SparqlRepositoryFactory.getRepository(cli.dbUrl, cli.repositoryId, cli.username, cli.password);
			
			DirectoryScanner scanner = new DirectoryScanner();
			scanner.setIncludes(new String[]{cli.inputFile.substring(cli.inputFile.startsWith("/") ? 1 : 0)});
			scanner.setBasedir(new File("/"));
			scanner.setCaseSensitive(false);
			scanner.scan();
			for(String inputFilePath : scanner.getIncludedFiles()) {
				SparqlLoader.uploadRdf("/" + inputFilePath, repo, cli.graphUri);
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

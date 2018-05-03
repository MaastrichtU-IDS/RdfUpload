package nl.unimaas.ids;

import java.io.File;

import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.CommandLineParser;
import org.apache.commons.cli.DefaultParser;
import org.apache.commons.cli.HelpFormatter;
import org.apache.commons.cli.Options;
import org.eclipse.rdf4j.repository.RepositoryConnection;
import org.eclipse.rdf4j.repository.sparql.SPARQLRepository;
import org.eclipse.rdf4j.rio.Rio;

public class RdfUpload {

	public static void main(String[] args) throws Exception {
		try {
			CommandLineParser commandLineParser = new DefaultParser();
			CommandLine commandLine = commandLineParser.parse(generateOptions(), args);
			
			final String filePath = commandLine.getOptionValue("inputFile");;
			
			SPARQLRepository repo;
			if(commandLine.hasOption("uep"))
				repo = new SPARQLRepository(commandLine.getOptionValue("ep"), commandLine.getOptionValue("uep"));
			else
				repo = new SPARQLRepository(commandLine.getOptionValue("ep"));
			
			repo.setUsernameAndPassword(commandLine.getOptionValue("un"), commandLine.getOptionValue("pw"));
			repo.initialize();
			
			
			try (RepositoryConnection conn = repo.getConnection()) {
				File inputFile = new File(filePath);
				if(!inputFile.exists())
					throw new IllegalArgumentException("Input file \"" + inputFile.getAbsolutePath() + "\" does not exist");
				if(!inputFile.canRead())
					throw new SecurityException("Can not read from input file \"" + inputFile.getAbsolutePath() + "\"");
				
				conn.add(new File(filePath), null, Rio.getParserFormatForFileName(inputFile.getName()).get());
			} catch (Exception e) {
				printUsage(e);
			}
		
			repo.shutDown();
		} catch (Exception e) {
			printUsage(e);
		}
	}
	
	private static void printUsage(Throwable t) {
		t.printStackTrace();
		new HelpFormatter().printHelp("java -jar Rdf4jUpload.jar]", generateOptions());
	}


	private static Options generateOptions() {
		Options options = new Options();
		options.addRequiredOption("if","inputFile", true, "Path to the RDF file to be imported");
		options.addRequiredOption("ep","endPoint", true, "SPARQL Endpoint URL");
		options.addOption("uep","updateEndPoint", true, "SPARQL Update Endpoint URL");
		options.addOption("un","userName", true, "Username used for authentication");
		options.addOption("pw","password", true, "Password used for authentication");
		return options;
	}

}

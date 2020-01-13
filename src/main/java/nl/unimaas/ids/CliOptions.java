package nl.unimaas.ids;

import picocli.CommandLine.Command;
import picocli.CommandLine.Option;

@Command(name = "rdf-upload")
public class CliOptions {
	@Option(names = { "-?", "--help" }, usageHelp = true, description = "Display a help message")
	boolean help = false;
	
	@Option(names= {"-if", "--inputFile"}, description = "Absolute path to RDF file to upload", required = true)
	String inputFile = null;

	@Option(names= {"-url", "--database-url"}, required = true,
			description = "URL of RDF4J Server or SPARQL endpoint to upload the RDF to. e.g. http://graphdb:7200/repositories/test/statements")
	String dbUrl = null;

	@Option(names= {"-rep", "--repositoryId"}, description = "RDF4J Repository ID for HTTPRepository file upload")
	String repositoryId = null;

	@Option(names= {"-un", "--username"}, description = "Username used for triplestore authentication")
	String username = null;

	@Option(names= {"-pw", "--password"}, description = "Password used for triplestore authentication")
	String password = null;

	@Option(names= {"-g", "--graph-uri"}, description = "Graph to upload the triples to. If nquads and this argument are provided, then this argument has priority")
	String graphUri = null;

}
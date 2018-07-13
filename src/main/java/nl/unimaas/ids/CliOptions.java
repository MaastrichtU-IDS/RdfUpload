package nl.unimaas.ids;

import picocli.CommandLine.Command;
import picocli.CommandLine.Option;

@Command(name = "rdfupload")
public class CliOptions {
	
	@Option(names = { "-?", "--help" }, usageHelp = true, description = "display a help message")
	boolean help = false;
	
	@Option(names= {"-if", "--inputFile"}, description = "RDF file path", required = true)
	String inputFile = null;
	
	@Option(names= {"-ep", "--endPoint"}, description = "SPARQL endpoint URL")
	String endpoint = null;
	
	@Option(names= {"-uep", "--updateEndPoint"}, description = "SPARQL udpate endpoint")
	String updateEndpoint = null;
	
	@Option(names= {"-un", "--userName"}, description = "Username userd for authentication")
	String userName = null;
	
	@Option(names= {"-pw", "--Password"}, description = "Password used for authentication")
	String passWord = null;

	@Option(names= {"-rep", "--repository"}, description = "Repository ID", required = true)
	String repository = null;

	@Option(names= {"-url", "--graphdb-url"}, description = "URL to access GraphDB (e.g.: http://localhost:7200)", required = true)
	String url = null;
	
}

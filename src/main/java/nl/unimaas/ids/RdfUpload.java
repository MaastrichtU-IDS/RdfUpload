package nl.unimaas.ids;

import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.CommandLineParser;
import org.apache.commons.cli.DefaultParser;
import org.apache.commons.cli.HelpFormatter;
import org.apache.commons.cli.Options;
import org.apache.jena.rdf.model.Model;
import org.apache.jena.rdf.model.Statement;
import org.apache.jena.rdf.model.StmtIterator;
import org.apache.jena.riot.RDFDataMgr;
import org.apache.xerces.impl.xpath.regex.RegularExpression;
import org.eclipse.rdf4j.repository.RepositoryConnection;
import org.eclipse.rdf4j.repository.sparql.SPARQLRepository;

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
			
			
			try {
				try (RepositoryConnection conn = repo.getConnection()) {
					Model model = RDFDataMgr.loadDataset(filePath).getDefaultModel();
					StmtIterator stmtIterator = model.listStatements();
					while (stmtIterator.hasNext()) {
						Statement stmt = stmtIterator.next();
		
						String insert = "INSERT DATA { <" + stmt.getSubject() + "> <" + stmt.getPredicate() + "> " + getObjectOrLiteral(stmt) + " }";
						System.out.println(insert);
						conn.prepareUpdate(insert).execute();
					}
				}
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


	static RegularExpression literalCheck = new RegularExpression("^([\"'0-9]|true|false)");

	private static String getObjectOrLiteral(Statement stmt) {
		return literalCheck.matches(stmt.getObject().toString()) ? "\"" + stmt.getObject() + "\""
				: "<" + stmt.getObject() + ">";
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

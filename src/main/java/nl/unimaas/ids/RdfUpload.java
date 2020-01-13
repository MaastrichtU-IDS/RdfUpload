package nl.unimaas.ids;

import java.io.File;
import java.util.Collection;
import java.util.Iterator;

import org.eclipse.rdf4j.repository.Repository;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.filefilter.DirectoryFileFilter;
import org.apache.commons.io.filefilter.RegexFileFilter;
import org.apache.tools.ant.DirectoryScanner;

import picocli.CommandLine;

public class RdfUpload {
	
	public static void main(String[] args) throws Exception {
		try {
			CliOptions cli = CommandLine.populateCommand(new CliOptions(), args);
			
			if(cli.help)
				printUsageAndExit();

			Repository repo = SparqlRepositoryFactory.getRepository(cli.dbUrl, cli.repositoryId, cli.username, cli.password);
//			File inputFile = new File(cli.inputFile);
			
			SparqlLoader.uploadRdf(cli.inputFile, repo, cli.graphUri);
			
//			if (inputFile.isDirectory()) {
//				System.out.println("before file filter ");
//				// If the provided input path is a directory, then we iterate over files if this dir
//				Collection<File> files = FileUtils.listFiles(
//						inputFile,
//						new RegexFileFilter(".*\\.(nt|nq|n3|ttl|rdf)(\\.gz)*"),
//						DirectoryFileFilter.DIRECTORY
//				);
//				System.out.println("before iterator directory: ");
//				System.out.println(files.size());
//				System.out.println(files.toString());
//				// Recursively iterate over files in the directory
//				Iterator<File> iterator = files.iterator();
//				while (iterator.hasNext()) {
//					File f = iterator.next();
//					System.out.println("Uploading: " + f);
//				}
//			}
			
//			DirectoryScanner scanner = new DirectoryScanner();
//			scanner.setIncludes(new String[]{cli.inputFile.substring(cli.inputFile.startsWith("/") ? 1 : 0)});
//			.(nt|nq|n3|ttl|rdf)
//			String[] includes = {"**\\*.nt"};
//			scanner.setIncludes(includes);
//			scanner.setBasedir(new File("/data"));
//			scanner.setCaseSensitive(false);
//			scanner.scan();
//			for(String inputFilePath : scanner.getIncludedFiles()) {
//				System.out.println("inputFilePath in scanner");
//				System.out.println(inputFilePath);
//				SparqlLoader.uploadRdf("/" + inputFilePath, repo, cli.graphUri);
//			}

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

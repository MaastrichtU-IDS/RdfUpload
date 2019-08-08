package nl.unimaas.ids;

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.filefilter.DirectoryFileFilter;
import org.apache.commons.io.filefilter.RegexFileFilter;
import org.eclipse.rdf4j.repository.Repository;
import org.eclipse.rdf4j.repository.RepositoryConnection;
import org.eclipse.rdf4j.rio.Rio;

import java.io.File;
import java.util.Collection;
import java.util.Iterator;

/**
 * A class to upload RDF to a RDF4J repository
 */
public class SparqlLoader {	

	public static void uploadRdf(String filePath, Repository repo) throws Exception {

//		try (RepositoryConnection conn = repo.getConnection()) {
		RepositoryConnection conn = repo.getConnection();
		File inputFile = new File(filePath);
		if(!inputFile.exists())
			throw new IllegalArgumentException("Input file \"" + inputFile.getAbsolutePath() + "\" does not exist");
		if(!inputFile.canRead())
			throw new SecurityException("Can not read from input file \"" + inputFile.getAbsolutePath() + "\"");

		if (inputFile.isDirectory()) {
			Collection<File> files = FileUtils.listFiles(
					inputFile,
					new RegexFileFilter(".*\\.(nt|nq|n3|ttl|rdf)(\\.gz)*"),
					DirectoryFileFilter.DIRECTORY
			);
			// Recursively iterate over files in the directory
			Iterator<File> iterator = files.iterator();
			while (iterator.hasNext()) {
				File f = iterator.next();
				System.out.println("Uploading: " + f);
				conn.add(f, null, Rio.getParserFormatForFileName(f.getName()).get());
			}
		} else {
			conn.add(new File(filePath), null, Rio.getParserFormatForFileName(inputFile.getName()).get());
		}
//		} catch (Exception e) { throw e; }

		repo.shutDown();
	}
}
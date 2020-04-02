import java.lang.ProcessBuilder;
import java.io.BufferedReader;
import java.lang.Object;
import java.io.InputStreamReader;
import java.util.List;
import java.util.ArrayList;
import java.util.StringJoiner;
public class Git {
    public static void main(String [] args){
        String message = args[0];
        List<String> gitadd = new ArrayList<>();
        gitadd.add("git");
        gitadd.add("add");
        gitadd.add(".");
        List<String> commit = new ArrayList<>();
        commit.add("git");
        commit.add("commit");
        commit.add("-m");
        commit.add(message);
        List<String> push = new ArrayList<>();
        push.add("git");
        push.add("push");
        push.add("origin");
        push.add("master");
        runCommandForOutput(gitadd);
        runCommandForOutput(commit);
        runCommandForOutput(push);

        
    }

    public static String runCommandForOutput(List<String> params) {
        ProcessBuilder pb = new ProcessBuilder(params);
        Process p;
        String result = "";
        try {
            p = pb.start();
            final BufferedReader reader = new BufferedReader(new InputStreamReader(p.getInputStream()));
    
            StringJoiner sj = new StringJoiner(System.getProperty("line.separator"));
            reader.lines().iterator().forEachRemaining(sj::add);
            result = sj.toString();
    
            p.waitFor();
            p.destroy();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }


}
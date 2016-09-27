import java.io.File;

public class test {
        public static void main(String[] args)
    {
        String path = "/apps/kz01/test";    // path of the folder you want to create
        if (args.length>1){
        	path=args[0];
        }
        File folder=new File(path);
        boolean exist=folder.exists();
        if(!exist){
            folder.mkdirs();
        }else{
        	System.out.println("folder already exist"+args[0]);
            System.out.println("folder already exist");
            System.out.println("folder already exist"+folder.getAbsolutePath());
        }
    }
}

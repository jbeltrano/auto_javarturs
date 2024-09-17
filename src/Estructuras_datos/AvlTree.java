package Estructuras_datos;

public class AvlTree {

    public TreeNode root;

    public AvlTree(){
        this.root = null;
    }
    
    public boolean isEmpty(){
        return root == null;
    }

    public static TreeNode insert(TreeNode nodo, int valor){

        if(nodo == null){
            return new TreeNode(valor);
        }

        if(valor < nodo.valor){
            nodo.left = insert(nodo.left, valor);
        }else if(valor > nodo.valor){
            nodo.right = insert(nodo.right, valor);
        }else{
            return nodo;
        }

        nodo.height = 1 + max(height(nodo.left), height(nodo.right));

        int balance = getBalance(nodo);

        if(balance > 1 && valor < nodo.left.valor){
            return rightRotate(nodo);
        }
        if(balance < -1 && valor >nodo.right.valor){
            return leftRotate(nodo);
        }

        if(balance > 1 && valor > nodo.left.valor){
            nodo.left = leftRotate(nodo.left);
            return rightRotate(nodo);
        }
        if(balance < -1 && valor < nodo.right.valor){
            nodo.right = rightRotate(nodo.right);
            return leftRotate(nodo);
        }

        return nodo;
    }

    public static int height(TreeNode n){
        if(n == null){
            return 0;
        }
        return n.height;
    }

    public static TreeNode rightRotate(TreeNode n){
        TreeNode x = n.left;
        TreeNode y = x.right;

        x.right = n;
        n.left = y;

        n.height = 1 + max(height(n.left),height(n.right));
        x.height = 1 + max(height(x.left), height(x.right));

        return x;
    }

    public static TreeNode leftRotate(TreeNode n){
        TreeNode x = n.right;
        TreeNode y = x.left;

        x.left = n;
        n.right = y;

        n.height = 1 + max(height(n.left), height(n.right));
        x.height = 1 + max(height(x.left),height(x.right));

        return x;
    }

    public static int getBalance(TreeNode n){
        if(n == null){
            return 0;
        }
        return height(n.left) - height(n.right);
    }

    public static int max(int x, int y){
        return (x >= y)?x:y;
    }

    public static TreeNode minValueNode(TreeNode node) {
        TreeNode current = node;

        // loop down to find the leftmost leaf
        while (current.left != null)
            current = current.left;

        return current;
    }

    
    public static TreeNode deleteNode(TreeNode root, int valor) {
        if (root == null)
            return root;

        
        if (valor < root.valor)
            root.left = deleteNode(root.left, valor);

        else if (valor > root.valor)
            root.right = deleteNode(root.right, valor);

        
        else {
            if ((root.left == null) || 
                (root.right == null)) {
                TreeNode temp = root.left != null ? 
                            root.left : root.right;

                if (temp == null) {
                    temp = root;
                    root = null;
                } else 
                    root = temp;
            } else {
                TreeNode temp = minValueNode(root.right);

                
                root.valor = temp.valor;

                root.right = deleteNode(root.right, temp.valor);
            }
        }

        if (root == null)
            return root;

        root.height = Math.max(height(root.left), 
                               height(root.right)) + 1;

        int balance = getBalance(root);

        if (balance > 1 && getBalance(root.left) >= 0)
            return rightRotate(root);

        if (balance > 1 && getBalance(root.left) < 0) {
            root.left = leftRotate(root.left);
            return rightRotate(root);
        }

        if (balance < -1 && getBalance(root.right) <= 0)
            return leftRotate(root);

        if (balance < -1 && getBalance(root.right) > 0) {
            root.right = rightRotate(root.right);
            return leftRotate(root);
        }

        return root;
    }

    public static TreeNode search(TreeNode root, int key){
        
        if (root == null || root.valor == key)
            return root;

        if (root.valor < key)
            return search(root.right, key);

        return search(root.left, key);
    }
}

class TreeNode{

    public int valor;
    public int height;
    public TreeNode left;
    public TreeNode right;

    public TreeNode(int k){
        this.valor = k;
        this.left = null;
        this.right = null;
        this.height = 1;
    }
}

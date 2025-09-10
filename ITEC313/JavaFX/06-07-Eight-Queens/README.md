## 06-07 — Eight Queens (N-Queens)

JavaFX visualization of the N‑Queens problem with step‑by‑step animation. Enter N (1–16), click "Solve Eight Queens" and watch queens placed while attacked squares are shown as dots.

### Run

- `mvn clean compile`
- `mvn test`
- `mvn javafx:run`

### Core solver (short and sharp)

```java
// Returns one solution: cols[row] = column index
static int[] solve(int n){
  int[] cols=new int[n];
  java.util.Arrays.fill(cols,-1);
  backtrack(0,cols,n);
  return cols;
}
static boolean backtrack(int row,int[] cols,int n){
  if(row==n) return true;
  for(int c=0;c<n;c++) if(isSafe(row,c,cols)){
    cols[row]=c;
    if(backtrack(row+1,cols,n)) return true;
    cols[row]=-1;
  }
  return false;
}
static boolean isSafe(int row,int col,int[] cols){
  for(int r=0;r<row;r++){
    int c=cols[r];
    if(c==col || Math.abs(r-row)==Math.abs(c-col)) return false;
  }
  return true;
}
```

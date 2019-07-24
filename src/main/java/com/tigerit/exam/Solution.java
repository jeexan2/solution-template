package com.tigerit.exam;




import java.util.*;


import static com.tigerit.exam.IO.*;

/**
 * All of your application logic should be placed inside this class.
 * Remember we will load your application from our custom container.
 * You may add private method inside this class but, make sure your
 * application's execution points start from inside run method.
 */
public class Solution implements Runnable {
    @Override
    public void run() {
        // your application entry point
        final Scanner scan = new Scanner(System.in);

        // Integer integer = readLineAsInteger();
        class Table{
            private String _tableName;

            private Integer _row,_column;
            private Dictionary<String,Integer> _columnIndex;
            private List<String> _columns;
            private List<List<Integer>> _tableRecords;

            public Table(){
                _columnIndex = new Hashtable<String, Integer>();
                _tableRecords = new ArrayList<List<Integer>>();
                _columns = new ArrayList<String>();

            }
            public void input(){

                this._tableName = readLine();
                this._column = scan.nextInt();
                this._row = scan.nextInt();
                for(Integer _col = 0; _col < _column; _col++){
                    String _colName = scan.next();
                    this._columnIndex.put(_colName,_col);
                    _columns.add(_colName);

                }
                for(Integer row = 0; row < _row; row++){
                    List<Integer> rowTable = new ArrayList<>();
                    for(Integer col = 0; col < _column; col++){
                        Integer num = scan.nextInt();
                        rowTable.add(num);
                    }
                    this._tableRecords.add(rowTable);
                }

            }



            //Getter Portion
            public String get_tableName(){return this._tableName;}
            public Integer get_row() {return this._row;}
            public Integer get_column(){return this._column;}
            public Dictionary<String,Integer> get_columnIndex() {return this._columnIndex;}
            public List<List<Integer>> get_tableRecords() {return this._tableRecords;}
            public List<String> get_columns(){return this._columns;}



        }

        class Query{
            private List<String> _inputLines;

            private List<String> _shortNameList;
            private List<String> _tableList;



            public Query(List<Table> _tables){
//                String line = readLine();
                _inputLines = new ArrayList<String>();
                _tableList  = new ArrayList<String>();
//                _columnList = new ArrayList<String>();
                _shortNameList = new ArrayList<String>();

                for(Integer i = 0; i <= 4; i++){
                    String qry = readLine();
                   if(i < 4) _inputLines.add(qry);
                }
                
                //  _tableList.add(commaSeparator(_inputLines.get(1)).get(1));
                String[] _tbl1 = spaceSeparator(_inputLines.get(1));
                String[] _tbl2 = spaceSeparator(_inputLines.get(2));
                _tableList.add(_tbl1[1]);
                _tableList.add(_tbl2[1]);

                if(_tbl1.length > 2){
                    _shortNameList.add(_tbl1[2]);
                }

                if(_tbl2.length > 2){
                    _shortNameList.add(_tbl2[2]);
                }

                String t1Cond;
                String t2Cond;
                String[] condLine = spaceSeparator(_inputLines.get(3));

                String str = _inputLines.get(3);

                String ar[] = str.split(" ");

                t1Cond= ar[1].split("\\.")[1];
                t2Cond = ar[3].split("\\.")[1];

                Table t1 = new Table();
                Table t2 = new Table();


                for(int i = 0; i < _tables.size();i++){
                    for(int j = 0; j < _tableList.size();j++){
                        if(_tables.get(i).get_tableName().equals(_tableList.get(j))){
                            if(j == 0) t1 = _tables.get(i);
                            else t2 = _tables.get(i);
                        }
                    }
                }

                Integer t1ConditionColumnIndex = t1.get_columnIndex().get(t1Cond);
                Integer t2ConditionColumnIndex = t2.get_columnIndex().get(t2Cond);


                List<Integer> rowsOfTable1 = new ArrayList<Integer>();
                List<Integer> rowsOfTable2 = new ArrayList<Integer>();
                for(int i = 0; i < t1.get_tableRecords().size();i++){
                    for(int j = 0; j < t2.get_tableRecords().size();j++){
                        if(t1.get_tableRecords().get(i).get(t1ConditionColumnIndex)
                                == t2.get_tableRecords().get(j).get(t2ConditionColumnIndex)){
                            rowsOfTable1.add(i);
                            rowsOfTable2.add(j);
                        }
                    }
                }
                List<Integer> colsOfTable1 = new ArrayList<Integer>();
                List<Integer> colsOfTable2 = new ArrayList<Integer>();
                //input line 1 solve to get columns (short name + big name)

                String[] line1Condition = _inputLines.get(0).split(" ");
                int size1 = t1.get_columnIndex().size();
                int size2 = t2.get_columnIndex().size();
                // printLine(line1Condition);
                if(line1Condition[1].equals("*")){
                    for(int i = 0; i < size1; i++)
                        colsOfTable1.add(i);
                    for(int i = 0; i < size2; i++)
                        colsOfTable2.add(i);
                }
                else {
                    for(int i = 1; i < line1Condition.length;i++) {

                        String tblColumn = "";
                        int ed = i < line1Condition.length - 1 ? line1Condition[i].length() - 1 :
                                line1Condition[i].length();
                         tblColumn= line1Condition[i].substring(0,ed);
                         String tbl = "";
                         String col = "";
                         tbl = tblColumn.split("\\.")[0];
                         col = tblColumn.split("\\.")[1];
                         if(tbl.equals(_shortNameList.get(0))){
                             colsOfTable1.add(t1.get_columnIndex().get(col));
                         }
                         else if(tbl.equals(_shortNameList.get(1))){
                            colsOfTable2.add(t2.get_columnIndex().get(col));
                         }

                    }

                }

                //Short Name check and printing

                //Column print
                for(int i = 0; i < colsOfTable1.size(); i++){

                    System.out.print(t1.get_columns().get(colsOfTable1.get(i))+ " ");
                }
                for(int j = 0; j < colsOfTable2.size();j++){
                    System.out.print(t2.get_columns().get(colsOfTable2.get(j))+" ");
                }
                System.out.println("");

               int iterationEnd = rowsOfTable1.size();

               for(int i = 0; i < iterationEnd; i++){
                   int rowT1 = rowsOfTable1.get(i);
                   int rowT2 = rowsOfTable2.get(i);
                   for(int c = 0; c < colsOfTable1.size(); c++)
                        System.out.print(t1.get_tableRecords().get(rowT1).get(colsOfTable1.get(c))+" ");

                   for(int c = 0; c < colsOfTable2.size();c++){
                       System.out.print(t2.get_tableRecords().get(rowT2).get(colsOfTable2.get(c)));
                       if(c < colsOfTable2.size() - 1)
                           System.out.print(" ");
                   }
                   printLine("");
               }
               printLine("");


            }


            private String[] spaceSeparator(String str){
                String[] ret = str.split(" ");
                return ret;
            }

        }

        Integer testCases = readLineAsInteger();
        for(Integer _case = 1; _case <= testCases;_case++){
            List<Table> _tables = new ArrayList<>();
            Integer tableNumbers = readLineAsInteger();
            for(Integer _tblIndx = 0; _tblIndx < tableNumbers;_tblIndx++){
                Table _table = new Table();
                _table.input();
                _tables.add(_table);

            }
            System.out.println("Test: "+ _case);
            Integer queryNumbers = readLineAsInteger();
            List<Query> queryList = new ArrayList<Query>();
            for(Integer qry = 0; qry < queryNumbers; qry++){
                Query query = new Query(_tables);
//                readLine();
                // queryList.add(query);
            }



        }



    }
}

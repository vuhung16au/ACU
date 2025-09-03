# Sample prompt to talk with AIs.

## The dataset

Given the dataset below in `supermarket.csv`

Filename: `supermarket.csv`
```csv
receipt_id	desserts	meats	juices	paper_goods	frozen_foods	snack_foods	canned goods	beer_wine_spirits	dairy	breads	produce
1	0	1	1	0	1	0	0	0	0	0	1
2	1	0	1	1	0	0	0	0	1	0	0
3	1	1	1	1	1	0	1	1	1	1	1
4	1	1	0	1	1	0	0	0	0	0	1
5	0	0	0	0	0	1	0	1	0	0	0
6	1	0	1	0	0	0	0	0	0	0	0
7	1	0	0	1	0	0	0	0	0	0	0
8	0	0	0	0	0	1	0	0	1	0	0
9	1	0	0	1	0	0	0	0	0	0	0
10	0	1	0	0	0	0	0	0	0	0	1
11	0	0	0	0	1	0	0	0	0	0	0
12	1	0	0	1	1	0	0	0	1	0	0
13	1	0	0	0	0	0	0	0	0	1	0
...
```

The actual dataset contains 100K rows.

My objective here is to identify frequent product combinations and generate actionable association rules from supermarket receipt data using the FP-Growth algorithm in Altair AI Studio (we are using verion 2025). 

Please create sequence of operators (in Altair AI Studio)
For each operators, suggest me what parameters should I set in order to get a good result. 

## The expected output

- A set of frequent itemsets
- A set of association rules

Save the process file in `outputs/supermarket.rmp`

## Additional requirements:
- Generate a RapidMiner process file (.rmp) for these operators, that is compatible with Altair AI Studio version 2025.
- The path to the dataset in Altair AI Studio is: `//ITEC632/Week03/supermarket.csv`

- Sample `.rmp` file content as follows
```xml
<?xml version="1.0" encoding="UTF-8"?><process version="10.4.000">
  <context>
    <input/>
    <output/>
    <macros/>
  </context>
  <operator activated="true" class="process" compatibility="10.4.000" expanded="true" name="Process">
    <parameter key="logverbosity" value="init"/>
    <parameter key="random_seed" value="2001"/>
    <parameter key="send_mail" value="never"/>
    <parameter key="notification_email" value=""/>
    <parameter key="process_duration_for_mail" value="30"/>
    <parameter key="encoding" value="SYSTEM"/>
    <process expanded="true">
      <operator activated="true" class="retrieve" compatibility="10.4.000" expanded="true" height="68" name="Retrieve Supermarket_Extracted" width="90" x="45" y="34">
        <parameter key="repository_entry" value="//My_First_Prediction/Supermarket_Extracted"/>
      </operator>
      <operator activated="true" class="blending:set_role" compatibility="10.4.000" expanded="true" height="82" name="Set Role" width="90" x="179" y="34">
        <list key="set_roles">
          <parameter key="receipt_id" value="id"/>
        </list>
      </operator>
      <operator activated="true" class="numerical_to_binominal" compatibility="10.4.000" expanded="true" height="82" name="Numerical to Binominal" width="90" x="313" y="34">
        <parameter key="attribute_filter_type" value="all"/>
        <parameter key="attribute" value=""/>
        <parameter key="attributes" value=""/>
        <parameter key="use_except_expression" value="false"/>
        <parameter key="value_type" value="numeric"/>
        <parameter key="use_value_type_exception" value="false"/>
        <parameter key="except_value_type" value="real"/>
        <parameter key="block_type" value="value_series"/>
        <parameter key="use_block_type_exception" value="false"/>
        <parameter key="except_block_type" value="value_series_end"/>
        <parameter key="invert_selection" value="false"/>
        <parameter key="include_special_attributes" value="false"/>
        <parameter key="min" value="0.0"/>
        <parameter key="max" value="0.0"/>
      </operator>
      <operator activated="true" class="concurrency:fp_growth" compatibility="10.4.000" expanded="true" height="82" name="FP-Growth" width="90" x="447" y="34">
        <parameter key="input_format" value="items in dummy coded columns"/>
        <parameter key="item_separators" value="|"/>
        <parameter key="use_quotes" value="false"/>
        <parameter key="quotes_character" value="&quot;"/>
        <parameter key="escape_character" value="\"/>
        <parameter key="trim_item_names" value="true"/>
        <parameter key="min_requirement" value="support"/>
        <parameter key="min_support" value="0.2"/>
        <parameter key="min_frequency" value="100"/>
        <parameter key="min_items_per_itemset" value="1"/>
        <parameter key="max_items_per_itemset" value="0"/>
        <parameter key="max_number_of_itemsets" value="1000000"/>
        <parameter key="find_min_number_of_itemsets" value="true"/>
        <parameter key="min_number_of_itemsets" value="100"/>
        <parameter key="max_number_of_retries" value="15"/>
        <parameter key="requirement_decrease_factor" value="0.9"/>
        <enumeration key="must_contain_list"/>
      </operator>
      <operator activated="true" class="create_association_rules" compatibility="10.4.000" expanded="true" height="82" name="Create Association Rules" width="90" x="581" y="34">
        <parameter key="criterion" value="confidence"/>
        <parameter key="min_confidence" value="0.75"/>
        <parameter key="min_criterion_value" value="0.8"/>
        <parameter key="gain_theta" value="2.0"/>
        <parameter key="laplace_k" value="1.0"/>
      </operator>
      <connect from_op="Retrieve Supermarket_Extracted" from_port="output" to_op="Set Role" to_port="example set input"/>
      <connect from_op="Set Role" from_port="example set output" to_op="Numerical to Binominal" to_port="example set input"/>
      <connect from_op="Numerical to Binominal" from_port="example set output" to_op="FP-Growth" to_port="example set"/>
      <connect from_op="FP-Growth" from_port="example set" to_port="result 1"/>
      <connect from_op="FP-Growth" from_port="frequent sets" to_op="Create Association Rules" to_port="item sets"/>
      <connect from_op="Create Association Rules" from_port="rules" to_port="result 2"/>
      <portSpacing port="source_input 1" spacing="0"/>
      <portSpacing port="sink_result 1" spacing="0"/>
      <portSpacing port="sink_result 2" spacing="0"/>
      <portSpacing port="sink_result 3" spacing="0"/>
      <description align="left" color="yellow" colored="false" height="302" resized="true" width="735" x="60" y="211">1) Which combination of support and confidence gives the most actionable rules?&lt;br&gt;Support = 0.2 and Confidence = 0.75 resulted in a good number of strong, interpretable rules with high lift and business relevance. This balance yields rules that are frequent enough to matter and confident enough to act on.&lt;br&gt;&lt;br&gt;2) Which rules have the highest lift?&lt;br&gt;The rule with the highest lift is:&lt;br&gt;beer_wine_spirits, frozen_foods, meats &amp;#8594; snack_foods&lt;br&gt;Support: 0.0484&lt;br&gt;Confidence: 0.848&lt;br&gt;Lift: 2.511&lt;br&gt;This rule shows that customers who buy alcohol, frozen foods, and meats are significantly more likely to also buy snack foods.&lt;br&gt;3) What patterns can be used for targeted promotions or store layout planning?&lt;br&gt;Items like beer_wine_spirits, snack_foods, and frozen_foods frequently co-occur, suggesting these categories could be placed closer together to encourage bundled purchases.</description>
    </process>
  </operator>
</process>

```

## (The hint) Additional requirements

The tasks I need to follow in Altair AI Studio looks like:

```
Tasks:
Data Preparation: 
Load the supermarket receipt dataset with binary (0/1) product indicators.
Use Set Role to assign the transaction ID column as id. 
Convert numeric attributes to binominal using Numerical to Binominal.

FP-Growth:
Apply FP-Growth, experimenting with different minimum support values (e.g., 0.1 to 0.3) to find optimal frequent item sets. 

Generate rules using Create Association Rules while adjusting confidence thresholds (e.g., 0.6 to 0.9) for meaningful results.
```

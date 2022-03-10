# Exasol AWS Glue Connector User Guide


## Data Mapping

This section shows how Exasol data types are mapped to Spark data types.

You can checkout [data types overview](https://docs.exasol.com/sql_references/data_types/datatypesoverview.htm) documentation for information on Exasol data types. Similarly [support data types](https://spark.apache.org/docs/latest/sql-ref-datatypes.html) documentation for more information on Spark types.

The following table shows the mapping of Exasol base types to Spark types:

| Exasol Column Type | Spark Field Type    |
|:-------------------|:--------------------|
| `BOOLEAN`          | `BooleanType`       |
| `CHAR(n)`          | `StringType`        |
| `VARCHAR(n)`       | `StringType`        |
| `DECIMAL(p, s)`    | `DecimalType(p, s)` |
| `DOUBLE PRECISION` | `DoubleType`        |
| `DATE`             | `DateType`          |
| `TIMESTAMP`        | `TimestampType`     |

The following Exasol types are not supported at the moment:

- `GEOMETRY[(srid)]`
- `HASHTYPE[(n BYTE | m BIT)]`
- `TIMESTAMP WITH LOCAL TIME ZONE`
- `INTERVAL YEAR [(p)] TO MONTH`
- `INTERVAL DAY [(p)] TO SECOND [(fp)]`

Exasol offers type aliases. These types don't change functionality and use one of the base type underneath. For example, type alias `BIGINT` is equal to the base type `DECIMAL(36, 0)`. Alias types in Exasol provide compatibility with other third-party products.

The following table shows the mapping of the Exasol alias types to Spark types:

| Exasol Column Type          | Exasol Base Type   | Spark Field Type     |
|:----------------------------|:-------------------|:---------------------|
| `BOOL`                      | `BOOLEAN`          | `BooleanType`        |
| `CHAR`                      | `CHAR(1)`          | `StringType`         |
| `CHAR VARYING(n)`           | `VARCHAR(n)`       | `StringType`         |
| `CHARACTER`                 | `CHAR(1)`          | `StringType`         |
| `CHARACTER VARYING(n)`      | `VARCHAR(n)`       | `StringType`         |
| `CHARACTER LARGE OBJECT`    | `VARCHAR(2000000)` | `StringType`         |
| `CHARACTER LARGE OBJECT(n)` | `VARCHAR(n)`       | `StringType`         |
| `CLOB`                      | `VARCHAR(2000000)` | `StringType`         |
| `CLOB(n)`                   | `VARCHAR(n)`       | `StringType`         |
| `DEC`                       | `DECIMAL(18, 0)`   | `DecimalType`        |
| `DEC(p)`                    | `DECIMAL(p, 0)`    | `DecimalType(p, 0)`  |
| `DEC(p, s)`                 | `DECIMAL(p, s)`    | `DecimalType(p, s)`  |
| `DECIMAL`                   | `DECIMAL(18, 0)`   | `DecimalType(18, 0)` |
| `DECIMAL(p)`                | `DECIMAL(p, 0)`    | `DecimalType(p, 0)`  |
| `DOUBLE`                    | `DOUBLE PRECISION` | `DoubleType`         |
| `FLOAT`                     | `DOUBLE PRECISION` | `DoubleType`         |
| `REAL`                      | `DOUBLE PRECISION` | `DoubleType`         |
| `BIGINT`                    | `DECIMAL(36, 0)`   | `DecimalType`        |
| `INT`                       | `DECIMAL(18, 0)`   | `LongType`           |
| `INTEGER`                   | `DECIMAL(18, 0)`   | `LongType`           |
| `SHORTINT`                  | `DECIMAL(9, 0)`    | `IntegerType`        |
| `SMALLINT`                  | `DECIMAL(9, 0)`    | `IntegerType`        |
| `TINYINT`                   | `DECIMAL(3, 0)`    | `ShortType`          |
| `LONG VARCHAR`              | `VARCHAR(2000000)` | `StringType`         |
| `NCHAR(n)`                  | `CHAR(n)`          | `StringType`         |
| `NVARCHAR(n)`               | `VARCHAR(n)`       | `StringType`         |
| `NVARCHAR2(n)`              | `VARCHAR(n)`       | `StringType`         |
| `VARCHAR2(n)`               | `VARCHAR(n)`       | `StringType`         |
| `NUMBER`                    | `DOUBLE PRECISION` | `DoubleType`         |
| `NUMBER(p)`                 | `DECIMAL(p, 0)`    | `DecimalType`        |
| `NUMBER(p, s)`              | `DECIMAL(p, s)`    | `DecimalType`        |
| `NUMERIC`                   | `DECIMAL(18, 0)`   | `DecimalType`        |
| `NUMERIC(p)`                | `DECIMAL(p, 0)`    | `DecimalType`        |
| `NUMERIC(p, s)`             | `DECIMAL(p, s)`    | `DecimalType`        |

The following Exasol alias types are not supported at the moment:

| Exasol Column Type | Exasol Base Type     |
|:-------------------|:---------------------|
| `HASHTYPE`         | `HASHTYPE (16 BYTE)` |

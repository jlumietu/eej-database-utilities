# eej-database-utilities
Database management utilities

This package main goal is to provide the connection to data sources creating some types of DaoSupports for hibernate, and the direct mapping between java data types and corresponding restrictions generators, even generic or backend database type dependent

The complete suite is built by using together eej-utilities, eej-spring-utilities and eej-database-utilities, but all this three are packaged separetely to make possible the reutilization of some of the components.

This eej-database utilities depends on Hibernate and eej-utilities.

When all pieces set, generating a Data Tables server-side pagination directly from data objects such as pojos, with full operational dynamic filtering is trivial.

The configuration is quite complex (TODO: shorthand classes with default simple configuration), but once done, getting a view page for each entity is easy and quick

// Initialize the customerdb database
db = db.getSiblingDB('customerdb');

// Create customers collection and insert sample data
db.createCollection('customers');

// Insert Australia and Sydney themed customer data
db.customers.insertMany([
    {
        firstName: "Alice",
        lastName: "Smith",
        city: "Sydney",
        country: "Australia"
    },
    {
        firstName: "Bob",
        lastName: "Johnson",
        city: "Melbourne",
        country: "Australia"
    },
    {
        firstName: "Charlie",
        lastName: "Williams",
        city: "Sydney",
        country: "Australia"
    },
    {
        firstName: "Diana",
        lastName: "Brown",
        city: "Brisbane",
        country: "Australia"
    },
    {
        firstName: "Eve",
        lastName: "Jones",
        city: "Perth",
        country: "Australia"
    },
    {
        firstName: "Frank",
        lastName: "Garcia",
        city: "Adelaide",
        country: "Australia"
    },
    {
        firstName: "Grace",
        lastName: "Miller",
        city: "Sydney",
        country: "Australia"
    },
    {
        firstName: "Henry",
        lastName: "Davis",
        city: "Canberra",
        country: "Australia"
    },
    {
        firstName: "Ivy",
        lastName: "Rodriguez",
        city: "Sydney",
        country: "Australia"
    },
    {
        firstName: "Jack",
        lastName: "Martinez",
        city: "Darwin",
        country: "Australia"
    }
]);

print("Database initialized with Australia and Sydney themed customer data!");
print("Total customers inserted: " + db.customers.count());
print("Customers in Sydney: " + db.customers.find({city: "Sydney"}).count());
print("Customers in Australia: " + db.customers.find({country: "Australia"}).count());

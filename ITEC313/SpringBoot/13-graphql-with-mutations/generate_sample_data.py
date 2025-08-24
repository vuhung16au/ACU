#!/usr/bin/env python3
"""
Sample Data Generator for GraphQL Book Database
Generates 1000 realistic authors and 2000 books
"""

import random
import uuid

# Real author names (mix of famous and common names)
FIRST_NAMES = [
    "James", "John", "Robert", "Michael", "William", "David", "Richard", "Joseph", "Thomas", "Christopher",
    "Charles", "Daniel", "Matthew", "Anthony", "Mark", "Donald", "Steven", "Paul", "Andrew", "Joshua",
    "Kenneth", "Kevin", "Brian", "George", "Edward", "Ronald", "Timothy", "Jason", "Jeffrey", "Ryan",
    "Jacob", "Gary", "Nicholas", "Eric", "Jonathan", "Stephen", "Larry", "Justin", "Scott", "Brandon",
    "Benjamin", "Samuel", "Frank", "Gregory", "Raymond", "Alexander", "Patrick", "Jack", "Dennis", "Jerry",
    "Tyler", "Aaron", "Jose", "Adam", "Nathan", "Henry", "Douglas", "Zachary", "Peter", "Kyle",
    "Walter", "Ethan", "Jeremy", "Harold", "Seth", "Christian", "Mason", "Austin", "Evan", "Jesse",
    "Mary", "Patricia", "Jennifer", "Linda", "Elizabeth", "Barbara", "Susan", "Jessica", "Sarah", "Karen",
    "Nancy", "Lisa", "Betty", "Helen", "Sandra", "Donna", "Carol", "Ruth", "Sharon", "Michelle",
    "Laura", "Emily", "Kimberly", "Deborah", "Dorothy", "Lisa", "Nancy", "Karen", "Betty", "Helen",
    "Sandra", "Donna", "Carol", "Ruth", "Sharon", "Michelle", "Laura", "Emily", "Kimberly", "Deborah",
    "Dorothy", "Lisa", "Nancy", "Karen", "Betty", "Helen", "Sandra", "Donna", "Carol", "Ruth",
    "Sharon", "Michelle", "Laura", "Emily", "Kimberly", "Deborah", "Dorothy", "Lisa", "Nancy", "Karen",
    "Emma", "Olivia", "Ava", "Isabella", "Sophia", "Charlotte", "Mia", "Amelia", "Harper", "Evelyn",
    "Abigail", "Emily", "Elizabeth", "Mila", "Ella", "Avery", "Sofia", "Camila", "Aria", "Scarlett",
    "Victoria", "Madison", "Luna", "Grace", "Chloe", "Penelope", "Layla", "Riley", "Zoey", "Nora",
    "Lily", "Eleanor", "Hannah", "Lillian", "Addison", "Aubrey", "Ellie", "Stella", "Natalie", "Zoe",
    "Leah", "Hazel", "Violet", "Aurora", "Savannah", "Audrey", "Brooklyn", "Bella", "Claire", "Skylar"
]

LAST_NAMES = [
    "Smith", "Johnson", "Williams", "Brown", "Jones", "Garcia", "Miller", "Davis", "Rodriguez", "Martinez",
    "Hernandez", "Lopez", "Gonzalez", "Wilson", "Anderson", "Thomas", "Taylor", "Moore", "Jackson", "Martin",
    "Lee", "Perez", "Thompson", "White", "Harris", "Sanchez", "Clark", "Ramirez", "Lewis", "Robinson",
    "Walker", "Young", "Allen", "King", "Wright", "Scott", "Torres", "Nguyen", "Hill", "Flores",
    "Green", "Adams", "Nelson", "Baker", "Hall", "Rivera", "Campbell", "Mitchell", "Carter", "Roberts",
    "Gomez", "Phillips", "Evans", "Turner", "Diaz", "Parker", "Cruz", "Edwards", "Collins", "Reyes",
    "Stewart", "Morris", "Morales", "Murphy", "Cook", "Rogers", "Gutierrez", "Ortiz", "Morgan", "Cooper",
    "Peterson", "Bailey", "Reed", "Kelly", "Howard", "Ramos", "Kim", "Cox", "Ward", "Richardson",
    "Watson", "Brooks", "Chavez", "Wood", "James", "Bennett", "Gray", "Mendoza", "Ruiz", "Hughes",
    "Price", "Alvarez", "Castillo", "Sanders", "Patel", "Myers", "Long", "Ross", "Foster", "Jimenez",
    "Powell", "Jenkins", "Perry", "Russell", "Sullivan", "Bell", "Coleman", "Butler", "Henderson", "Barnes",
    "Gonzales", "Fisher", "Vasquez", "Simmons", "Romero", "Jordan", "Patterson", "Alexander", "Hamilton", "Graham",
    "Reynolds", "Griffin", "Wallace", "Moreno", "West", "Cole", "Hayes", "Bryant", "Herrera", "Gibson",
    "Ellis", "Tran", "Medina", "Aguilar", "Stevens", "Murray", "Ford", "Castro", "Marshall", "Owens",
    "Harrison", "Fernandez", "Mcdonald", "Woods", "Washington", "Kennedy", "Wells", "Vargas", "Henry", "Chen",
    "Freeman", "Webb", "Tucker", "Guzman", "Burns", "Crawford", "Olson", "Simpson", "Porter", "Hunter",
    "Gordon", "Mendez", "Silva", "Shaw", "Snyder", "Mason", "Dixon", "Munoz", "Hunt", "Hicks",
    "Holmes", "Palmer", "Wagner", "Black", "Robertson", "Boyd", "Rose", "Stone", "Salazar", "Fox",
    "Warren", "Mills", "Meyer", "Rice", "Schmidt", "Garza", "Daniels", "Ferguson", "Nichols", "Stephens",
    "Soto", "Weaver", "Ryan", "Gardner", "Payne", "Grant", "Dunn", "Kelley", "Spencer", "Hawkins",
    "Arnold", "Pierce", "Vazquez", "Hansen", "Peters", "Santos", "Hart", "Bradley", "Knight", "Elliott",
    "Cunningham", "Duncan", "Armstrong", "Hudson", "Carroll", "Lane", "Riley", "Andrews", "Alvarado", "Ray",
    "Delgado", "Berry", "Perkins", "Hoffman", "Johnston", "Matthews", "Pena", "Richards", "Contreras", "Willis",
    "Carpenter", "Lawrence", "Sandoval", "Guerrero", "George", "Chapman", "Rios", "Estrada", "Ortega", "Watkins",
    "Greene", "Nunez", "Wheeler", "Valdez", "Harper", "Burke", "Larson", "Santiago", "Maldonado", "Morrison",
    "Franklin", "Carlson", "Austin", "Dominguez", "Carr", "Lawson", "Jacobs", "Obrien", "Lynch", "Singh",
    "Vega", "Bishop", "Montgomery", "Oliver", "Jensen", "Harvey", "Williamson", "Gilbert", "Dean", "Sims",
    "Espinoza", "Howell", "Li", "Wong", "Reid", "Hanson", "Le", "Mccoy", "Garrett", "Burton",
    "Fuller", "Castillo", "Snyder", "Gibson", "Mason", "Warren", "Kennedy", "Dixon", "Ramos", "Reyes"
]

# Book title templates and words
BOOK_TITLE_TEMPLATES = [
    "The {adj} {noun}",
    "{adj} {noun}",
    "A {adj} {noun}",
    "The {noun} of {place}",
    "{place} {noun}",
    "The {adj} {noun} in {place}",
    "{person} and the {noun}",
    "The {adj} {noun} {verb}",
    "{time} {noun}",
    "The {noun} {verb}",
    "{adj} {time}",
    "The {adj} {person}",
    "{place} {time}",
    "The {noun} {verb} {place}",
    "{adj} {noun} {verb}",
    "The {time} {noun}",
    "{person} {verb}",
    "The {adj} {place}",
    "{noun} in {place}",
    "The {verb} {noun}"
]

ADJECTIVES = [
    "Mysterious", "Ancient", "Hidden", "Lost", "Golden", "Dark", "Bright", "Silent", "Loud", "Swift",
    "Slow", "Warm", "Cold", "Deep", "Shallow", "High", "Low", "Far", "Near", "Big",
    "Small", "New", "Old", "Young", "Beautiful", "Ugly", "Happy", "Sad", "Angry", "Calm",
    "Wild", "Tame", "Rich", "Poor", "Strong", "Weak", "Brave", "Cowardly", "Wise", "Foolish",
    "Kind", "Cruel", "Gentle", "Rough", "Smooth", "Hard", "Soft", "Light", "Heavy", "Empty",
    "Full", "Open", "Closed", "Clean", "Dirty", "Fresh", "Stale", "Sweet", "Sour", "Bitter",
    "Hot", "Warm", "Cool", "Cold", "Wet", "Dry", "Wide", "Narrow", "Long", "Short",
    "Thick", "Thin", "Round", "Square", "Sharp", "Dull", "Bright", "Dim", "Clear", "Cloudy",
    "Quiet", "Noisy", "Busy", "Lazy", "Active", "Passive", "Creative", "Destructive", "Helpful", "Harmful",
    "Peaceful", "Violent", "Orderly", "Chaotic", "Simple", "Complex", "Easy", "Difficult", "Safe", "Dangerous",
    "Familiar", "Strange", "Common", "Rare", "Normal", "Abnormal", "Natural", "Artificial", "Real", "Fake",
    "True", "False", "Right", "Wrong", "Good", "Bad", "Perfect", "Imperfect", "Complete", "Incomplete"
]

NOUNS = [
    "Book", "Story", "Tale", "Adventure", "Journey", "Quest", "Mystery", "Secret", "Treasure", "Map",
    "Key", "Door", "Window", "House", "Castle", "Tower", "Bridge", "Road", "Path", "Forest",
    "Mountain", "River", "Ocean", "Sea", "Island", "Desert", "Valley", "Cave", "Lake", "Spring",
    "Tree", "Flower", "Garden", "Field", "Meadow", "Hill", "Cliff", "Beach", "Shore", "Harbor",
    "City", "Town", "Village", "Street", "Avenue", "Road", "Highway", "Bridge", "Tunnel", "Station",
    "School", "Library", "Museum", "Theater", "Church", "Temple", "Palace", "Cottage", "Cabin", "Lodge",
    "Inn", "Hotel", "Restaurant", "Shop", "Market", "Store", "Factory", "Office", "Studio", "Workshop",
    "Kitchen", "Bedroom", "Living Room", "Dining Room", "Bathroom", "Attic", "Basement", "Garage", "Shed", "Barn",
    "Table", "Chair", "Bed", "Sofa", "Desk", "Shelf", "Mirror", "Clock", "Lamp", "Candle",
    "Picture", "Painting", "Photograph", "Letter", "Note", "Diary", "Journal", "Newspaper", "Magazine", "Card",
    "Phone", "Computer", "Television", "Radio", "Camera", "Watch", "Ring", "Necklace", "Bracelet", "Earring",
    "Hat", "Coat", "Shoe", "Boot", "Glove", "Scarf", "Belt", "Bag", "Wallet", "Purse",
    "Car", "Boat", "Plane", "Train", "Bicycle", "Motorcycle", "Bus", "Truck", "Van", "Taxi",
    "Dog", "Cat", "Bird", "Fish", "Horse", "Cow", "Pig", "Sheep", "Goat", "Chicken",
    "Lion", "Tiger", "Bear", "Wolf", "Fox", "Deer", "Rabbit", "Squirrel", "Mouse", "Rat",
    "Snake", "Lizard", "Frog", "Turtle", "Crab", "Lobster", "Shark", "Whale", "Dolphin", "Octopus",
    "Butterfly", "Bee", "Ant", "Spider", "Fly", "Mosquito", "Dragonfly", "Ladybug", "Beetle", "Cricket",
    "Apple", "Orange", "Banana", "Grape", "Strawberry", "Cherry", "Peach", "Pear", "Lemon", "Lime",
    "Bread", "Cake", "Cookie", "Pie", "Ice Cream", "Chocolate", "Candy", "Sugar", "Salt", "Pepper",
    "Water", "Milk", "Juice", "Coffee", "Tea", "Wine", "Beer", "Soup", "Salad", "Steak",
    "Pizza", "Hamburger", "Hot Dog", "Sandwich", "Taco", "Burrito", "Sushi", "Pasta", "Rice", "Potato"
]

PLACES = [
    "London", "Paris", "Rome", "New York", "Tokyo", "Sydney", "Berlin", "Madrid", "Amsterdam", "Vienna",
    "Prague", "Budapest", "Warsaw", "Moscow", "St. Petersburg", "Istanbul", "Cairo", "Dubai", "Mumbai", "Delhi",
    "Bangkok", "Singapore", "Hong Kong", "Seoul", "Beijing", "Shanghai", "Melbourne", "Brisbane", "Perth", "Adelaide",
    "Auckland", "Vancouver", "Toronto", "Montreal", "Calgary", "Edmonton", "Winnipeg", "Ottawa", "Quebec", "Halifax",
    "San Francisco", "Los Angeles", "Chicago", "Houston", "Phoenix", "Philadelphia", "San Antonio", "San Diego", "Dallas", "San Jose",
    "Austin", "Jacksonville", "Fort Worth", "Columbus", "Charlotte", "San Francisco", "Indianapolis", "Seattle", "Denver", "Washington",
    "Boston", "El Paso", "Nashville", "Detroit", "Oklahoma City", "Portland", "Las Vegas", "Memphis", "Louisville", "Baltimore",
    "Milwaukee", "Albuquerque", "Tucson", "Fresno", "Sacramento", "Atlanta", "Kansas City", "Long Beach", "Colorado Springs", "Raleigh",
    "Miami", "Virginia Beach", "Omaha", "Oakland", "Minneapolis", "Tulsa", "Arlington", "Tampa", "New Orleans", "Wichita",
    "Cleveland", "Bakersfield", "Aurora", "Anaheim", "Honolulu", "Santa Ana", "Corpus Christi", "Riverside", "Lexington", "Stockton",
    "Henderson", "Saint Paul", "St. Louis", "Milwaukee", "Portland", "Orlando", "Irvine", "Cincinnati", "Newark", "Durham",
    "Chula Vista", "Toledo", "Fort Wayne", "St. Petersburg", "Laredo", "Jersey City", "Chandler", "Madison", "Lubbock", "Scottsdale",
    "Reno", "Buffalo", "Gilbert", "Glendale", "North Las Vegas", "Winston-Salem", "Chesapeake", "Norfolk", "Fremont", "Garland",
    "Irving", "Hialeah", "Richmond", "Boise", "Spokane", "Baton Rouge", "Tacoma", "San Bernardino", "Grand Rapids", "Huntsville",
    "Salt Lake City", "Fresno", "Modesto", "Des Moines", "Oxnard", "Fontana", "Moreno Valley", "Huntington Beach", "Glendale", "Santa Clarita",
    "Garden Grove", "Oceanside", "Rancho Cucamonga", "Santa Rosa", "Ontario", "Lancaster", "Elk Grove", "Corona", "Eugene", "Palmdale",
    "Salinas", "Springfield", "Pasadena", "Fort Collins", "Hayward", "Pomona", "Cary", "Rockford", "Alexandria", "Escondido",
    "McKinney", "Kansas City", "Joliet", "Sunnyvale", "Torrance", "Bridgeport", "Lakewood", "Hollywood", "Paterson", "Naperville",
    "Syracuse", "Mesquite", "Dayton", "Savannah", "Clarksville", "Orange", "Pasadena", "Fullerton", "Killeen", "Frisco",
    "Hampton", "McAllen", "Warren", "Bellevue", "West Valley City", "Columbia", "Olathe", "Sterling Heights", "New Haven", "Miramar",
    "Waco", "Thousand Oaks", "Cedar Rapids", "Charleston", "Visalia", "Topeka", "Elizabeth", "Gainesville", "Thornton", "Roseville",
    "Carrollton", "Coral Springs", "Stamford", "Simi Valley", "Concord", "Hartford", "Kent", "Lafayette", "Midland", "Surprise",
    "Denton", "Victorville", "Evansville", "Vallejo", "Berkeley", "Antioch", "Allentown", "Abilene", "Beaumont", "Provo",
    "Peoria", "Lansing", "Downey", "El Monte", "Independence", "Murfreesboro", "Springfield", "Fairfield", "Athens", "Round Rock",
    "Norman", "Columbia", "Brandon", "Cary", "Boise", "Rochester", "Manteca", "Irvine", "Arlington", "Albany",
    "Vacaville", "Temecula", "Lancaster", "North Hempstead", "Erie", "Fargo", "Jurupa Valley", "Rialto", "Billings", "Yonkers",
    "Spokane", "Renton", "Wichita", "Gilbert", "Amarillo", "Cape Coral", "Lubbock", "Modesto", "San Bernardino", "Durham",
    "Tacoma", "Fontana", "Oxnard", "Moreno Valley", "Fresno", "Huntington Beach", "Glendale", "Santa Clarita", "Garden Grove", "Oceanside",
    "Rancho Cucamonga", "Santa Rosa", "Ontario", "Lancaster", "Elk Grove", "Corona", "Eugene", "Palmdale", "Salinas", "Springfield",
    "Pasadena", "Fort Collins", "Hayward", "Pomona", "Cary", "Rockford", "Alexandria", "Escondido", "McKinney", "Kansas City",
    "Joliet", "Sunnyvale", "Torrance", "Bridgeport", "Lakewood", "Hollywood", "Paterson", "Naperville", "Syracuse", "Mesquite",
    "Dayton", "Savannah", "Clarksville", "Orange", "Pasadena", "Fullerton", "Killeen", "Frisco", "Hampton", "McAllen",
    "Warren", "Bellevue", "West Valley City", "Columbia", "Olathe", "Sterling Heights", "New Haven", "Miramar", "Waco", "Thousand Oaks"
]

PERSONS = [
    "Alice", "Bob", "Charlie", "Diana", "Edward", "Fiona", "George", "Helen", "Ian", "Julia",
    "Kevin", "Linda", "Michael", "Nancy", "Oliver", "Patricia", "Quinn", "Rachel", "Samuel", "Tina",
    "Ulysses", "Victoria", "William", "Xena", "Yvonne", "Zachary", "Amelia", "Benjamin", "Charlotte", "David",
    "Eleanor", "Frank", "Grace", "Henry", "Isabella", "Jack", "Kate", "Liam", "Mia", "Noah",
    "Olivia", "Paul", "Quinn", "Ruby", "Sophia", "Thomas", "Uma", "Vincent", "Wendy", "Xavier",
    "Yara", "Zoe", "Adam", "Bella", "Caleb", "Daisy", "Ethan", "Faith", "Gavin", "Hope",
    "Isaac", "Jade", "Kai", "Luna", "Mason", "Nova", "Owen", "Penny", "Quincy", "Rose",
    "Seth", "Tara", "Uriah", "Violet", "Wyatt", "Xander", "Yuki", "Zara", "Aiden", "Brooke",
    "Caden", "Dawn", "Evan", "Flora", "Gage", "Hazel", "Ivy", "Jake", "Kira", "Leo",
    "Maya", "Nate", "Opal", "Parker", "Quinn", "Raven", "Sage", "Talia", "Ursula", "Vera",
    "Wade", "Xena", "Yara", "Zane", "Aria", "Blake", "Cora", "Dex", "Eva", "Finn",
    "Gigi", "Hank", "Iris", "Jace", "Kara", "Lane", "Mila", "Nash", "Ora", "Pax",
    "Quill", "Remy", "Sage", "Tess", "Uri", "Vale", "Wren", "Xander", "Yuki", "Zara",
    "Ace", "Bree", "Cruz", "Dove", "Echo", "Faye", "Grey", "Haven", "Indigo", "Jett",
    "Kai", "Lark", "Moss", "Nyx", "Onyx", "Poe", "Quill", "Raven", "Sage", "Talon",
    "Uma", "Vale", "Wren", "Xander", "Yara", "Zephyr", "Aurora", "Blaze", "Cedar", "Dawn",
    "Echo", "Flame", "Gale", "Haven", "Iris", "Jade", "Kai", "Luna", "Moss", "Nova",
    "Onyx", "Poe", "Quill", "Raven", "Sage", "Talon", "Uma", "Vale", "Wren", "Xander"
]

VERBS = [
    "Rises", "Falls", "Flows", "Burns", "Shines", "Glows", "Moves", "Stops", "Starts", "Ends",
    "Begins", "Continues", "Changes", "Stays", "Goes", "Comes", "Returns", "Leaves", "Arrives", "Departs",
    "Walks", "Runs", "Jumps", "Flies", "Swims", "Crawls", "Climbs", "Descends", "Ascends", "Floats",
    "Sinks", "Dives", "Surfaces", "Emerges", "Disappears", "Vanishes", "Appears", "Shows", "Hides", "Reveals",
    "Conceals", "Opens", "Closes", "Locks", "Unlocks", "Breaks", "Fixes", "Builds", "Destroys", "Creates",
    "Makes", "Takes", "Gives", "Receives", "Sends", "Brings", "Carries", "Lifts", "Drops", "Throws",
    "Catches", "Holds", "Releases", "Pushes", "Pulls", "Turns", "Spins", "Rotates", "Moves", "Stops",
    "Starts", "Begins", "Ends", "Finishes", "Completes", "Accepts", "Rejects", "Approves", "Denies", "Agrees",
    "Disagrees", "Likes", "Dislikes", "Loves", "Hates", "Wants", "Needs", "Has", "Gets", "Loses",
    "Finds", "Seeks", "Searches", "Discovers", "Explores", "Investigates", "Studies", "Learns", "Teaches", "Explains",
    "Describes", "Tells", "Says", "Speaks", "Talks", "Whispers", "Shouts", "Sings", "Plays", "Works",
    "Rests", "Sleeps", "Wakes", "Dreams", "Thinks", "Knows", "Understands", "Believes", "Doubts", "Wonders",
    "Asks", "Answers", "Questions", "Replies", "Responds", "Reacts", "Acts", "Behaves", "Performs", "Does",
    "Makes", "Creates", "Builds", "Constructs", "Designs", "Plans", "Prepares", "Organizes", "Arranges", "Orders",
    "Sorts", "Chooses", "Selects", "Picks", "Decides", "Determines", "Resolves", "Solves", "Fixes", "Repairs",
    "Heals", "Cures", "Treats", "Helps", "Assists", "Supports", "Protects", "Defends", "Guards", "Watches",
    "Observes", "Witnesses", "Sees", "Looks", "Views", "Examines", "Inspects", "Checks", "Tests", "Tries",
    "Attempts", "Tries", "Strives", "Works", "Labors", "Toils", "Struggles", "Fights", "Battles", "Wars",
    "Wins", "Loses", "Fails", "Succeeds", "Achieves", "Accomplishes", "Reaches", "Attains", "Gains", "Earns"
]

TIMES = [
    "Morning", "Afternoon", "Evening", "Night", "Dawn", "Dusk", "Midnight", "Noon", "Sunrise", "Sunset",
    "Spring", "Summer", "Autumn", "Winter", "January", "February", "March", "April", "May", "June",
    "July", "August", "September", "October", "November", "December", "Monday", "Tuesday", "Wednesday", "Thursday",
    "Friday", "Saturday", "Sunday", "Yesterday", "Today", "Tomorrow", "Past", "Present", "Future", "Ancient",
    "Modern", "Contemporary", "Historical", "Traditional", "Classical", "Neoclassical", "Renaissance", "Medieval", "Victorian", "Edwardian",
    "Georgian", "Tudor", "Gothic", "Romanesque", "Baroque", "Rococo", "Art Nouveau", "Art Deco", "Modernist", "Postmodern",
    "Digital", "Analog", "Mechanical", "Electrical", "Electronic", "Nuclear", "Solar", "Wind", "Hydro", "Geothermal",
    "Fossil", "Renewable", "Sustainable", "Organic", "Natural", "Artificial", "Synthetic", "Chemical", "Biological", "Physical",
    "Mathematical", "Logical", "Philosophical", "Theological", "Psychological", "Sociological", "Anthropological", "Archaeological", "Geological", "Astronomical",
    "Meteorological", "Oceanographic", "Botanical", "Zoological", "Microbiological", "Genetic", "Molecular", "Atomic", "Subatomic", "Quantum",
    "Relativistic", "Classical", "Newtonian", "Einsteinian", "Darwinian", "Freudian", "Jungian", "Marxist", "Keynesian", "Friedmanian",
    "Aristotelian", "Platonic", "Socratic", "Hegelian", "Kantian", "Nietzschean", "Sartrean", "Camusian", "Kafkaesque", "Orwellian",
    "Hitchcockian", "Spielbergian", "Tarantinoesque", "Kubrickian", "Felliniesque", "Bergmanesque", "Tarkovskian", "Kurosawan", "Miyazakian", "Nolanian",
    "Scorsesean", "Coppolan", "Lucasian", "Spielbergian", "Cameronian", "Jacksonian", "Nolanian", "Villeneuvian", "Del Toroan", "Cuaronian"
]

def generate_author_name():
    """Generate a realistic author name"""
    first_name = random.choice(FIRST_NAMES)
    last_name = random.choice(LAST_NAMES)
    return first_name, last_name

def generate_book_title():
    """Generate a realistic book title"""
    template = random.choice(BOOK_TITLE_TEMPLATES)
    
    if "{adj}" in template:
        template = template.replace("{adj}", random.choice(ADJECTIVES))
    if "{noun}" in template:
        template = template.replace("{noun}", random.choice(NOUNS))
    if "{place}" in template:
        template = template.replace("{place}", random.choice(PLACES))
    if "{person}" in template:
        template = template.replace("{person}", random.choice(PERSONS))
    if "{verb}" in template:
        template = template.replace("{verb}", random.choice(VERBS))
    if "{time}" in template:
        template = template.replace("{time}", random.choice(TIMES))
    
    return template

def generate_page_count():
    """Generate realistic page count"""
    # Most books are between 200-500 pages
    weights = [
        (100, 200, 0.1),   # Short books
        (200, 300, 0.3),   # Medium-short books
        (300, 400, 0.3),   # Medium books
        (400, 500, 0.2),   # Medium-long books
        (500, 800, 0.08),  # Long books
        (800, 1200, 0.02)  # Very long books
    ]
    
    for min_pages, max_pages, weight in weights:
        if random.random() < weight:
            return random.randint(min_pages, max_pages)
    
    return random.randint(200, 400)  # Default fallback

def generate_sample_data():
    """Generate SQL insert statements for 1000 authors and 2000 books"""
    
    # Generate authors
    authors = []
    for i in range(1000):
        author_id = f"author-{i+3}"  # Start from 3 since we have 2 existing authors
        first_name, last_name = generate_author_name()
        authors.append((author_id, first_name, last_name))
    
    # Generate books
    books = []
    for i in range(2000):
        book_id = f"book-{i+3}"  # Start from 3 since we have 2 existing books
        title = generate_book_title()
        page_count = generate_page_count()
        author_id = random.choice(authors)[0]  # Randomly assign to an author
        books.append((book_id, title, page_count, author_id))
    
    return authors, books

def write_sql_file(authors, books, filename="sample_data.sql"):
    """Write the generated data to a SQL file"""
    
    with open(filename, 'w') as f:
        f.write("-- Sample Data for GraphQL Book Database\n")
        f.write("-- Generated automatically\n\n")
        
        # Insert authors
        f.write("-- Insert sample authors\n")
        for author_id, first_name, last_name in authors:
            f.write(f"INSERT INTO authors (id, first_name, last_name) VALUES ('{author_id}', '{first_name}', '{last_name}');\n")
        
        f.write("\n-- Insert sample books\n")
        for book_id, title, page_count, author_id in books:
            # Escape single quotes in title
            title = title.replace("'", "''")
            f.write(f"INSERT INTO books (id, name, page_count, author_id) VALUES ('{book_id}', '{title}', {page_count}, '{author_id}');\n")
        
        f.write("\n-- Sample data generation complete\n")
        f.write(f"-- Total authors: {len(authors)}\n")
        f.write(f"-- Total books: {len(books)}\n")

if __name__ == "__main__":
    print("Generating sample data...")
    authors, books = generate_sample_data()
    
    print(f"Generated {len(authors)} authors and {len(books)} books")
    
    # Write to SQL file
    write_sql_file(authors, books, "docker/sample_data.sql")
    
    print("Sample data written to docker/sample_data.sql")
    print("You can now run this SQL file to populate your database with sample data.")

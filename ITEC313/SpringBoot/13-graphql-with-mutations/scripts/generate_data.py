#!/usr/bin/env python3
"""
Script to generate realistic sample data for the GraphQL Book Database.
This script generates 1000 authors and 2000 books with realistic names, titles, and page counts.
"""

import random
import uuid

# Sample data arrays for generating realistic content
first_names = [
    "James", "John", "Robert", "Michael", "William", "David", "Richard", "Joseph", "Thomas", "Christopher",
    "Mary", "Patricia", "Jennifer", "Linda", "Elizabeth", "Barbara", "Susan", "Jessica", "Sarah", "Karen",
    "Emma", "Olivia", "Ava", "Isabella", "Sophia", "Charlotte", "Mia", "Amelia", "Harper", "Evelyn",
    "Alexander", "Benjamin", "Daniel", "Matthew", "Andrew", "Joshua", "Ryan", "Nicholas", "Tyler", "Sean",
    "Grace", "Chloe", "Zoe", "Lily", "Hannah", "Layla", "Riley", "Aria", "Avery", "Sofia"
]

last_names = [
    "Smith", "Johnson", "Williams", "Brown", "Jones", "Garcia", "Miller", "Davis", "Rodriguez", "Martinez",
    "Hernandez", "Lopez", "Gonzalez", "Wilson", "Anderson", "Thomas", "Taylor", "Moore", "Jackson", "Martin",
    "Lee", "Perez", "Thompson", "White", "Harris", "Sanchez", "Clark", "Ramirez", "Lewis", "Robinson",
    "Walker", "Young", "Allen", "King", "Wright", "Scott", "Torres", "Nguyen", "Hill", "Flores",
    "Green", "Adams", "Nelson", "Baker", "Hall", "Rivera", "Campbell", "Mitchell", "Carter", "Roberts"
]

book_titles = [
    "The {adj} {noun}", "{adj} {noun}", "A {adj} {noun}", "The {noun} of {place}",
    "{place} {noun}", "The {adj} {noun} in {place}", "{person} and the {noun}",
    "The {adj} {noun} Chronicles", "Beyond the {noun}", "The {adj} {noun} Legacy",
    "In the {adj} {place}", "The {noun} and the {adj} {noun}", "{person}'s {adj} {noun}",
    "The {adj} {noun} Mystery", "A {adj} {noun} Tale", "The {noun} of {person}",
    "{place}: The {adj} {noun}", "The {adj} {noun} Quest", "Through the {adj} {noun}",
    "The {noun} in {place}", "The {adj} {noun} Saga"
]

adjectives = [
    "Mysterious", "Ancient", "Hidden", "Lost", "Golden", "Dark", "Bright", "Silent", "Loud", "Swift",
    "Warm", "Cold", "Deep", "High", "Big", "Small", "New", "Old", "Beautiful", "Happy",
    "Eternal", "Forgotten", "Sacred", "Cursed", "Magical", "Enchanted", "Mystical", "Divine", "Infinite", "Timeless",
    "Whispering", "Roaring", "Dancing", "Flying", "Running", "Walking", "Sleeping", "Awakening", "Rising", "Falling",
    "Crystal", "Silver", "Bronze", "Iron", "Wooden", "Stone", "Glass", "Paper", "Leather", "Silk"
]

nouns = [
    "Book", "Story", "Tale", "Adventure", "Journey", "Quest", "Mystery", "Secret", "Treasure", "Map",
    "House", "Castle", "Tower", "Bridge", "Road", "Forest", "Mountain", "River", "Ocean", "Island",
    "Kingdom", "Empire", "Realm", "World", "Universe", "Dimension", "Portal", "Gate", "Door", "Window",
    "Heart", "Soul", "Mind", "Spirit", "Dream", "Vision", "Memory", "Promise", "Hope", "Faith",
    "Shadow", "Light", "Fire", "Water", "Earth", "Wind", "Storm", "Rain", "Snow", "Sun"
]

places = [
    "London", "Paris", "Rome", "New York", "Tokyo", "Sydney", "Berlin", "Madrid", "Amsterdam", "Vienna",
    "Prague", "Budapest", "Warsaw", "Moscow", "Istanbul", "Cairo", "Dubai", "Mumbai", "Delhi", "Bangkok",
    "Shanghai", "Seoul", "Singapore", "Hong Kong", "Toronto", "Vancouver", "Montreal", "Melbourne", "Brisbane", "Perth",
    "Athens", "Barcelona", "Milan", "Florence", "Venice", "Naples", "Dublin", "Edinburgh", "Glasgow", "Cardiff",
    "Oslo", "Stockholm", "Copenhagen", "Helsinki", "Reykjavik", "Warsaw", "Krakow", "Gdansk", "Wroclaw", "Poznan"
]

persons = [
    "Alice", "Bob", "Charlie", "Diana", "Edward", "Fiona", "George", "Helen", "Ian", "Julia",
    "Kevin", "Linda", "Michael", "Nancy", "Oliver", "Patricia", "Quinn", "Rachel", "Samuel", "Tina",
    "Victor", "Wendy", "Xavier", "Yvonne", "Zachary", "Amelia", "Benjamin", "Charlotte", "Daniel", "Eleanor",
    "Frederick", "Grace", "Henry", "Isabella", "James", "Katherine", "Liam", "Madeline", "Nathan", "Olivia",
    "Peter", "Quinn", "Rose", "Sebastian", "Theresa", "Ulysses", "Victoria", "William", "Xenia", "Yasmine"
]

def generate_title():
    """Generate a realistic book title using templates and random word selection."""
    template = random.choice(book_titles)
    template = template.replace("{adj}", random.choice(adjectives))
    template = template.replace("{noun}", random.choice(nouns))
    template = template.replace("{place}", random.choice(places))
    template = template.replace("{person}", random.choice(persons))
    return template

def generate_page_count():
    """Generate a realistic page count between 150 and 800 pages."""
    return random.randint(150, 800)

def generate_authors(count=1000):
    """Generate a list of authors with realistic names."""
    authors = []
    for i in range(count):
        author_id = f"author-{i+3}"  # Start from 3 since we already have 2 authors
        first_name = random.choice(first_names)
        last_name = random.choice(last_names)
        authors.append((author_id, first_name, last_name))
    return authors

def generate_books(count=2000, authors=None):
    """Generate a list of books with realistic titles and page counts."""
    if authors is None:
        authors = generate_authors()
    
    books = []
    for i in range(count):
        book_id = f"book-{i+3}"  # Start from 3 since we already have 2 books
        title = generate_title()
        page_count = generate_page_count()
        author_id = random.choice(authors)[0]
        books.append((book_id, title, page_count, author_id))
    return books

def generate_sql_file(authors, books, output_file="sample_data.sql"):
    """Generate SQL INSERT statements and write them to a file."""
    with open(output_file, "w") as f:
        f.write("-- Sample Data for GraphQL Book Database\n")
        f.write(f"-- Generated by generate_data.py\n")
        f.write(f"-- {len(authors)} authors and {len(books)} books\n\n")
        
        f.write("-- Insert sample authors\n")
        for author_id, first_name, last_name in authors:
            f.write(f"INSERT INTO authors (id, first_name, last_name) VALUES ('{author_id}', '{first_name}', '{last_name}');\n")
        
        f.write("\n-- Insert sample books\n")
        for book_id, title, page_count, author_id in books:
            # Escape single quotes in titles
            title = title.replace("'", "''")
            f.write(f"INSERT INTO books (id, name, page_count, author_id) VALUES ('{book_id}', '{title}', {page_count}, '{author_id}');\n")
    
    print(f"Generated SQL file: {output_file}")
    print(f"Authors: {len(authors)}")
    print(f"Books: {len(books)}")

def main():
    """Main function to generate sample data."""
    print("Generating sample data for GraphQL Book Database...")
    
    # Generate authors and books
    authors = generate_authors(1000)
    books = generate_books(2000, authors)
    
    # Generate SQL file
    generate_sql_file(authors, books, "sample_data.sql")
    
    print("Sample data generation completed!")
    print("You can now append the generated SQL to your init.sql file.")

if __name__ == "__main__":
    main()

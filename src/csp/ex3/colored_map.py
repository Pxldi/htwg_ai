import constraint

problem = constraint.Problem()

# Values
color = ['rot', 'grün', 'blau', 'gelb']

# Variables
state = ['Baden Württemberg',
         'Bayern',
         'Berlin',
         'Brandenburg',
         'Bremen',
         'Hamburg',
         'Hessen',
         'Mecklenburg-Vorpommern',
         'Niedersachsen',
         'Nordrhein Westfalen',
         'Rheinland-Pfalz',
         'Saarland',
         'Sachsen',
         'Sachsen-Anhalt',
         'Schleswig-Holstein',
         'Thüringen']

problem.addVariables(state, color)

problem.addConstraint(lambda a, b, c, d: a != b and a != c and a != d,
                      ('Baden Württemberg', 'Bayern', 'Hessen', 'Rheinland-Pfalz'))

problem.addConstraint(lambda a, b, c, d: a != b and a != c and a != d,
                      ('Bayern', 'Baden Württemberg', 'Hessen', 'Thüringen'))

problem.addConstraint(lambda a, b: a != b,
                      ('Berlin', 'Brandenburg'))

problem.addConstraint(lambda a, b, c, d, e, f: a != b and a != c and a != d and a != e and a != f,
                      ('Brandenburg', 'Berlin', 'Mecklenburg-Vorpommern', 'Sachsen-Anhalt', 'Sachsen', 'Niedersachsen'))

problem.addConstraint(lambda a, b: a != b,
                      ('Bremen', 'Niedersachsen'))

problem.addConstraint(lambda a, b, c: a != b and a != c,
                      ('Hamburg', 'Niedersachsen', 'Schleswig-Holstein'))

problem.addConstraint(lambda a, b, c, d, e, f, g: a != b and a != c and a != d and a != e and a != f and a != g,
                      ('Hessen', 'Baden Württemberg', 'Bayern', 'Rheinland-Pfalz', 'Nordrhein Westfalen',
                       'Niedersachsen', 'Thüringen'))

problem.addConstraint(lambda a, b, c, d: a != b and a != c and a != d,
                      ('Mecklenburg-Vorpommern', 'Brandenburg', 'Niedersachsen', 'Schleswig-Holstein'))

problem.addConstraint(lambda a, b, c, d, e, f, g, h, i, j:
                      a != b and a != c and a != d and a != e and a != f and a != g and a != h and a != i and a != j,
                      ('Niedersachsen', 'Brandenburg', 'Mecklenburg-Vorpommern', 'Schleswig-Holstein', 'Hamburg',
                       'Bremen', 'Nordrhein Westfalen', 'Hessen', 'Sachsen-Anhalt', 'Thüringen'))

problem.addConstraint(lambda a, b, c, d: a != b and a != c and a != d,
                      ('Nordrhein Westfalen', 'Hessen', 'Rheinland-Pfalz', 'Niedersachsen'))

problem.addConstraint(lambda a, b, c, d, e: a != b and a != c and a != d and a != e,
                      ('Rheinland-Pfalz', 'Baden Württemberg', 'Hessen', 'Nordrhein Westfalen', 'Saarland'))

problem.addConstraint(lambda a, b: a != b,
                      ('Saarland', 'Rheinland-Pfalz'))

problem.addConstraint(lambda a, b, c, d, e: a != b and a != c and a != d and a != e,
                      ('Sachsen', 'Brandenburg', 'Sachsen-Anhalt', 'Thüringen', 'Bayern'))

problem.addConstraint(lambda a, b, c, d, e: a != b and a != c and a != d and a != e,
                      ('Sachsen-Anhalt', 'Brandenburg', 'Sachsen', 'Niedersachsen', 'Thüringen'))

problem.addConstraint(lambda a, b, c, d: a != b and a != c and a != d,
                      ('Schleswig-Holstein', 'Hamburg', 'Niedersachsen', 'Mecklenburg-Vorpommern'))

problem.addConstraint(lambda a, b, c, d, e, f: a != b and a != c and a != d and a != e and a != f,
                      ('Thüringen', 'Sachsen', 'Sachsen-Anhalt', 'Niedersachsen', 'Hessen', 'Bayern'))

solutions = problem.getSolutions()

print("Solutions: {}".format(len(solutions)))

sol = {}
for k, v in solutions[0].items():
    if v in sol:
        sol[v].append(k)
    else:
        sol[v] = [k]

sol = dict(sorted(sol.items()))
print(sol)

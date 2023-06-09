import constraint

problem = constraint.Problem()

# Values
room = [1, 2, 3, 4]

# Variables
teacher = ['Maier', 'Huber', 'Müller', 'Schmid']
subject = ['Deutsch', 'Englisch', 'Mathematik', 'Physik']

# Add variables to the problems values
problem.addVariables(teacher, room)
problem.addVariables(subject, room)

# Constraints: values of all variables have to be different
problem.addConstraint(constraint.AllDifferentConstraint(), teacher)
problem.addConstraint(constraint.AllDifferentConstraint(), subject)

# Specific constraints:
# Herr Maier prüft nie in Raum 4
problem.addConstraint(lambda a: a != 4, ['Maier'])
# Herr Müller prüft immer Deutsch
problem.addConstraint(lambda a, b: a == b, ('Müller', 'Deutsch'))
# Herr Schmid und Herr Müller prüfen nicht in benachbarten Räumen
problem.addConstraint(lambda a, b: abs(a - b) >= 2, ('Schmid', 'Müller'))
# Frau Huber prüft Mathematik
problem.addConstraint(lambda a, b: a == b, ('Huber', 'Mathematik'))
# Physik wird immer in Raum 4 geprüft
problem.addConstraint(lambda a: a == 4, ['Physik'])
# Deutsch und Englisch werden nicht in Raum 1 geprüft
problem.addConstraint(lambda a, b: a != 1 and b != 1, ('Deutsch', 'Englisch'))

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

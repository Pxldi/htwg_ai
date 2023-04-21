import constraint


def solve(rectangles, big_rectangle_width, big_rectangle_height):
    problem = constraint.Problem()

    # Add variables
    for i, (width, height) in enumerate(rectangles):
        # check if rectangle can fit in big rectangle
        if width <= big_rectangle_width and height <= big_rectangle_height:
            problem.addVariable(f'x_{i}', range(big_rectangle_width - width + 1))
            problem.addVariable(f'y_{i}', range(big_rectangle_height - height + 1))
            problem.addVariable(f"o{i}", [0, 1])
        elif height <= big_rectangle_width and width <= big_rectangle_height:
            problem.addVariable(f'x_{i}', range(big_rectangle_width - height + 1))
            problem.addVariable(f'y_{i}', range(big_rectangle_height - width + 1))
            problem.addVariable(f"o{i}", [0, 1])
        # o0 = horizontal
        # o1 = vertical

        # print(f"x{i}", range(big_rectangle[0] - max(currRect) + 1), range(big_rectangle[1] - max(currRect) + 1))

    # Constraints
    for i, rect1 in enumerate(rectangles):
        for j, rect2 in enumerate(rectangles):
            if i < j:
                problem.addConstraint(
                    lambda x1, y1, o1, x2, y2, o2: check_overlap(x1, y1, o1, x2, y2, o2, rect1, rect2),
                    (f"x{i}", f"y{i}", f"o{i}", f"x{j}", f"y{j}", f"o{j}"))

    solutions = problem.getSolutions()

    if len(solutions) == 0:
        print("No solution found.")
        return

    print("Solutions: {}".format(len(solutions)))

    for i, currRect in enumerate(rectangles):
        orientation = "horizontal" if solutions[0][f"o{i}"] == 0 else "vertical"
        print(f"Rectangle {i}: {currRect},"
              f"Position: ({solutions[0][f'x{i}']},"
              f"{solutions[0][f'y{i}']}),"
              f"Orientation: {orientation}")


def check_overlap(x1, y1, o1, x2, y2, o2, rect1, rect2):
    width1 = rect1[o1] if o1 == 0 else rect1[1 - o1]
    height1 = rect1[1 - o1] if o1 == 0 else rect1[o1]
    width2 = rect2[o2] if o2 == 0 else rect2[1 - o2]
    height2 = rect2[1 - o2] if o2 == 0 else rect2[o2]

    horizontal_overlap = not (x1 + width1 > x2 and x1 < x2 + width2)
    vertical_overlap = not (y1 + height1 > y2 and y1 < y2 + height2)

    return horizontal_overlap and vertical_overlap


solve([(6, 4), (8, 1), (4, 1), (5, 2), (2, 2), (3, 2)],
      big_rectangle_width=7, big_rectangle_height=8)

package me.rogerioferreira.sudoku.game;

import java.util.ArrayList;
import java.util.List;

import me.rogerioferreira.sudoku.Point;

public class Board {
  static final int DEFAULT_SIZE = 9;
  private int size;
  int regionSize;
  int totalSpaces;
  int totalAssignments = 0;

  private List<List<Space>> spaces;

  public Board(int size) {
    this.size = size;
    this.regionSize = this.computeRegionSize();
    this.totalSpaces = this.size * this.size;

    this.createSpaces();
  }

  private int computeRegionSize() {
    return (int) Math.sqrt(size);
  }

  public SpaceRegion computeRegion(Point point) {
    var regionX = (int) (point.x() / this.regionSize);
    var regionY = (int) (point.y() / this.regionSize);

    return new SpaceRegion(regionX, regionY);
  }

  public boolean isBoardComplete() {
    return this.totalAssignments == this.totalSpaces && !this.hasInvalidAssignments();
  }

  private void createSpaces() {
    this.spaces = new ArrayList<>(this.size);

    for (int i = 0; i < this.size; i++) {
      this.spaces.add(new ArrayList<>(this.size));

      for (int j = 0; j < this.size; j++) {
        var space = new Space();
        space.region = this.computeRegion(new Point(i, j));
        this.spaces.get(i).add(space);
      }
    }
  }

  private boolean hasInvalidAssignments() {
    return this.spaces
        .stream()
        .flatMap(columnSpaces -> columnSpaces.stream())
        .anyMatch(space -> !space.getIsValid());

  }

  public Space assignSpace(boolean isInitial, Point point, int value) {
    var column = this.spaces.get(point.x());
    var space = column.get(point.y());
    var oldValue = space.value;

    space.point = point;
    space.isValid = true;
    space.isFixed = isInitial;
    space.value = value;

    if (oldValue != null && oldValue != value) {
      this.revalidateLineValue(point, oldValue);
      this.revalidateColumnValue(point, oldValue);
      this.revalidateRegion(point, oldValue);
    }

    this.validateMove(space, point, value);

    return space;
  }

  public void clearSpace(Point point) {
    var space = this.spaces.get(point.x()).get(point.y());

    if (space.value != null) {
      this.revalidateLineValue(point, space.value);
      this.revalidateColumnValue(point, space.value);
      this.revalidateRegion(point, space.value);
    }

    space.limpar();
  }

  // Run all the line making revalidations on values with same value that are
  // invalid
  private void revalidateLineValue(Point selfPoint, int value) {
    for (int x = 0; x < this.size; x++) {
      if (x == selfPoint.x()) {
        continue;
      }

      var space = this.spaces.get(x).get(selfPoint.y());

      if (space.value != null && space.value == value && !space.isValid) {
        var hasColumnDuplicate = this.spaces.get(x)
            .stream()
            .filter(columnSpace -> columnSpace.value != null && columnSpace.value == value)
            .count() > 1;

        var region = this.computeRegion(new Point(x, selfPoint.y()));

        // Do not validate region if it is invalid already
        var hasRegionDuplicate = hasColumnDuplicate || this.spaces
            .stream()
            .flatMap(columnSpaces -> columnSpaces.stream())
            .filter(regionSpace -> regionSpace.region.x() == region.x() && regionSpace.region.y() == region.y())
            .filter(regionSpace -> regionSpace.value != null && regionSpace.value == value)
            .count() > 1;

        space.isValid = !hasColumnDuplicate && !hasRegionDuplicate;
      }
    }
  }

  private void revalidateColumnValue(Point selfPoint, int value) {
    for (int y = 0; y < this.size; y++) {
      if (y == selfPoint.y()) {
        continue;
      }

      var space = this.spaces.get(selfPoint.x()).get(y);

      if (space.value != null && space.value == value && !space.isValid) {
        int localY = y;
        var hasColumnDuplicate = this.spaces
            .stream()
            .map(columnSpaces -> columnSpaces.get(localY))
            .filter(lineSpace -> lineSpace.value != null && lineSpace.value == value)
            .count() > 1;

        var region = this.computeRegion(new Point(selfPoint.x(), y));

        var hasRegionDuplicate = hasColumnDuplicate || this.spaces
            .stream()
            .flatMap(columnSpaces -> columnSpaces.stream())
            .filter(regionSpace -> regionSpace.region.x() == region.x() && regionSpace.region.y() == region.y())
            .filter(regionSpace -> regionSpace.value != null && regionSpace.value == value)
            .count() > 1;

        space.isValid = !hasColumnDuplicate && !hasRegionDuplicate;
      }
    }
  }

  public void revalidateRegion(Point selfPoint, int value) {

  }

  public void validateMove(Space spaceAssigned, Point point, int value) {
    // validate column
    for (int x = 0; x < this.size; x++) {
      if (x == point.x()) {
        continue;
      }

      var space = this.spaces.get(x).get(point.y());

      if (space.value != null && space.value == value) {
        spaceAssigned.isValid = space.isValid = false;
      }
    }

    // validate row
    for (int y = 0; y < this.size; y++) {
      if (y == point.y()) {
        continue;
      }

      var space = this.spaces.get(point.x()).get(y);

      if (space.value != null && space.value == value) {
        spaceAssigned.isValid = space.isValid = false;
      }
    }

    // validate region
    var region = this.computeRegion(point);
    System.out.println("Region: " + region);

    this.spaces
        .stream()
        .flatMap(columnSpaces -> {
          return columnSpaces
              .stream()
              .filter(columnSpace -> columnSpace.point != spaceAssigned.point)
              .filter(columnSpace -> columnSpace.region.x() == region.x() &&
                  columnSpace.region.y() == region.y());
        })
        .filter(regionSpace -> regionSpace.value != null && regionSpace.value == value)
        .forEach(regionSpace -> {
          spaceAssigned.isValid = regionSpace.isValid = false;
        });
  }

  public int getSize() {
    return this.size;
  }

  public int getRegionSize() {
    return this.regionSize;
  }

  public Space getSpace(Point point) {
    if (point.x() < 0 || point.x() >= this.size || point.y() < 0 || point.y() >= this.size) {
      return null;
    }

    return this.spaces.get(point.x()).get(point.y());
  }
}

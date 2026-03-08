# Layouts Guide for .NET MAUI

## Layout Basics

Layouts arrange child elements (controls) on the screen. MAUI provides several layout types for different scenarios.

## Common Layouts

| Layout | Use Case | Performance |
|--------|----------|-------------|
| **VerticalStackLayout** | Stack items vertically | Fast |
| **HorizontalStackLayout** | Stack items horizontally | Fast |
| **Grid** | Complex responsive layouts with rows/columns | Fast |
| **FlexLayout** | Responsive wrapping layouts | Good |
| **AbsoluteLayout** | Precise positioning with coordinates | Fast |

## VerticalStackLayout

Stacks children from top to bottom.

```xml
<VerticalStackLayout Padding="20" Spacing="10">
    <Label Text="Name:" />
    <Entry Placeholder="Enter name" />
    
    <Label Text="Email:" />
    <Entry Placeholder="Enter email" />
    
    <Button Text="Submit" />
</VerticalStackLayout>
```

**Properties**:
- `Spacing`: Gap between children (default: 0)
- `Padding`: Space around the layout

## HorizontalStackLayout

Stacks children from left to right.

```xml
<HorizontalStackLayout Spacing="10" Padding="20">
    <Image Source="avatar.png" WidthRequest="50" HeightRequest="50" />
    <Label Text="John Doe" VerticalOptions="Center" />
    <Button Text="Follow" VerticalOptions="Center" />
</HorizontalStackLayout>
```

## Grid

Most powerful layout for responsive UIs with rows and columns.

### Basic Grid (2x2)

```xml
<Grid>
    <Grid.RowDefinitions>
        <RowDefinition Height="Auto" />
        <RowDefinition Height="*" />
    </Grid.RowDefinitions>
    
    <Grid.ColumnDefinitions>
        <ColumnDefinition Width="*" />
        <ColumnDefinition Width="*" />
    </Grid.ColumnDefinitions>
    
    <!-- Row 0, Column 0 -->
    <Label Grid.Row="0" Grid.Column="0" Text="Top Left" />
    
    <!-- Row 0, Column 1 -->
    <Label Grid.Row="0" Grid.Column="1" Text="Top Right" />
    
    <!-- Row 1, spans both columns -->
    <Label Grid.Row="1" Grid.Column="0" Grid.ColumnSpan="2" 
           Text="Bottom (spans full width)" />
</Grid>
```

### Row/Column Sizing

| Size | Behavior | Example |
|------|----------|---------|
| **Auto** | Size to content | `Height="Auto"` |
| **\*** (Star) | Proportional | `Width="*"` (fill available space) |
| **2\*** | Double proportion | `Width="2*"` (2x more than 1*) |
| **Absolute** | Fixed pixels | `Width="100"` |

**Example with Mixed Sizing**:
```xml
<Grid.ColumnDefinitions>
    <ColumnDefinition Width="Auto" />     <!-- Sized to content -->
    <ColumnDefinition Width="*" />        <!-- Takes remaining space -->
    <ColumnDefinition Width="100" />      <!-- Fixed 100 pixels -->
</Grid.ColumnDefinitions>
```

### Form Layout with Grid

```xml
<Grid Padding="20" RowSpacing="15" ColumnSpacing="10">
    <Grid.RowDefinitions>
        <RowDefinition Height="Auto" />
        <RowDefinition Height="Auto" />
        <RowDefinition Height="Auto" />
        <RowDefinition Height="Auto" />
    </Grid.RowDefinitions>
    
    <Grid.ColumnDefinitions>
        <ColumnDefinition Width="Auto" />   <!-- Labels -->
        <ColumnDefinition Width="*" />      <!-- Input fields -->
    </Grid.ColumnDefinitions>
    
    <!-- Row 0: Name -->
    <Label Grid.Row="0" Grid.Column="0" Text="Name:" VerticalOptions="Center" />
    <Entry Grid.Row="0" Grid.Column="1" Placeholder="Enter name" />
    
    <!-- Row 1: Email -->
    <Label Grid.Row="1" Grid.Column="0" Text="Email:" VerticalOptions="Center" />
    <Entry Grid.Row="1" Grid.Column="1" Placeholder="Enter email" />
    
    <!-- Row 2: Phone -->
    <Label Grid.Row="2" Grid.Column="0" Text="Phone:" VerticalOptions="Center" />
    <Entry Grid.Row="2" Grid.Column="1" Placeholder="Enter phone" />
    
    <!-- Row 3: Button spans both columns -->
    <Button Grid.Row="3" Grid.Column="0" Grid.ColumnSpan="2" 
            Text="Save" />
</Grid>
```

### Dashboard Grid

```xml
<Grid RowSpacing="10" ColumnSpacing="10" Padding="10">
    <Grid.RowDefinitions>
        <RowDefinition Height="*" />
        <RowDefinition Height="*" />
    </Grid.RowDefinitions>
    
    <Grid.ColumnDefinitions>
        <ColumnDefinition Width="*" />
        <ColumnDefinition Width="*" />
    </Grid.ColumnDefinitions>
    
    <!-- 4 equal tiles -->
    <Frame Grid.Row="0" Grid.Column="0" BackgroundColor="LightBlue">
        <Label Text="Tile 1" HorizontalOptions="Center" />
    </Frame>
    
    <Frame Grid.Row="0" Grid.Column="1" BackgroundColor="LightGreen">
        <Label Text="Tile 2" HorizontalOptions="Center" />
    </Frame>
    
    <Frame Grid.Row="1" Grid.Column="0" BackgroundColor="LightCoral">
        <Label Text="Tile 3" HorizontalOptions="Center" />
    </Frame>
    
    <Frame Grid.Row="1" Grid.Column="1" BackgroundColor="LightYellow">
        <Label Text="Tile 4" HorizontalOptions="Center" />
    </Frame>
</Grid>
```

## FlexLayout

Wraps children when they don't fit on one line (like CSS Flexbox).

```xml
<FlexLayout Wrap="Wrap" JustifyContent="SpaceAround" Padding="10">
    <Button Text="Button 1" Margin="5" />
    <Button Text="Button 2" Margin="5" />
    <Button Text="Button 3" Margin="5" />
    <Button Text="Button 4" Margin="5" />
    <Button Text="Button 5" Margin="5" />
    <!-- Wraps to next line if screen is narrow -->
</FlexLayout>
```

**Properties**:
- `Direction`: Row (horizontal) or Column (vertical)
- `Wrap`: NoWrap, Wrap, Reverse
- `JustifyContent`: Start, Center, End, SpaceBetween, SpaceAround
- `AlignItems`: Start, Center, End, Stretch

## AbsoluteLayout

Position elements with exact X, Y coordinates.

```xml
<AbsoluteLayout>
    <!-- Position at (50, 50), size 100x100 -->
    <BoxView Color="Red"
             AbsoluteLayout.LayoutBounds="50,50,100,100" />
    
    <!-- Position at (200, 100), auto-sized -->
    <Label Text="Positioned Label"
           AbsoluteLayout.LayoutBounds="200,100,AutoSize,AutoSize" />
</AbsoluteLayout>
```

**Use sparingly** - hard to maintain and not responsive.

## Layout Options

Control how controls behave within layouts:

```xml
<!-- Vertical alignment -->
<Label VerticalOptions="Start" />      <!-- Top -->
<Label VerticalOptions="Center" />     <!-- Middle -->
<Label VerticalOptions="End" />        <!-- Bottom -->
<Label VerticalOptions="Fill" />       <!-- Stretch -->

<!-- Horizontal alignment -->
<Label HorizontalOptions="Start" />    <!-- Left -->
<Label HorizontalOptions="Center" />   <!-- Center -->
<Label HorizontalOptions="End" />      <!-- Right -->
<Label HorizontalOptions="Fill" />     <!-- Stretch -->

<!-- Both -->
<Label VerticalOptions="CenterAndExpand" />  <!-- Center + take extra space -->
```

## Padding and Margin

```xml
<!-- Padding: Space INSIDE control -->
<Frame Padding="20">
    <Label Text="Text inside frame" />
</Frame>

<!-- Margin: Space OUTSIDE control -->
<Label Text="Label with margin" Margin="10" />

<!-- Specific sides -->
<Frame Padding="10,20,10,20">  <!-- Left, Top, Right, Bottom -->
    <Label Text="Custom padding" />
</Frame>

<!-- Named syntax -->
<Frame>
    <Frame.Padding>
        <Thickness Left="10" Top="20" Right="10" Bottom="20" />
    </Frame.Padding>
</Frame>
```

## Nested Layouts

Combine layouts for complex UIs:

```xml
<Grid Padding="20" RowSpacing="15">
    <Grid.RowDefinitions>
        <RowDefinition Height="Auto" />  <!-- Header -->
        <RowDefinition Height="*" />     <!-- Content -->
        <RowDefinition Height="Auto" />  <!-- Footer -->
    </Grid.RowDefinitions>
    
    <!-- Header with horizontal items -->
    <HorizontalStackLayout Grid.Row="0" Spacing="10">
        <Image Source="logo.png" WidthRequest="40" />
        <Label Text="My App" FontSize="24" VerticalOptions="Center" />
    </HorizontalStackLayout>
    
    <!-- Main content area -->
    <ScrollView Grid.Row="1">
        <VerticalStackLayout Spacing="10">
            <Label Text="Content goes here" />
            <!-- More content -->
        </VerticalStackLayout>
    </ScrollView>
    
    <!-- Footer with buttons -->
    <Grid Grid.Row="2" ColumnSpacing="10">
        <Grid.ColumnDefinitions>
            <ColumnDefinition Width="*" />
            <ColumnDefinition Width="*" />
        </Grid.ColumnDefinitions>
        
        <Button Grid.Column="0" Text="Cancel" />
        <Button Grid.Column="1" Text="Save" />
    </Grid>
</Grid>
```

## Responsive Design

### Device-Specific Layouts

```xml
<ContentPage>
    <OnPlatform x:TypeArguments="View">
        <!-- Android layout -->
        <On Platform="Android">
            <StackLayout Padding="10">
                <Label Text="Android Layout" />
            </StackLayout>
        </On>
        
        <!-- iOS layout -->
        <On Platform="iOS">
            <StackLayout Padding="20">
                <Label Text="iOS Layout" />
            </StackLayout>
        </On>
    </OnPlatform>
</ContentPage>
```

### Adaptive Grid

```xml
<!-- 2 columns on wide screens, 1 column on narrow -->
<Grid x:Name="responsiveGrid">
    <Grid.ColumnDefinitions>
        <ColumnDefinition Width="*" />
        <ColumnDefinition Width="{OnIdiom Phone=0, Tablet=*, Desktop=*}" />
    </Grid.ColumnDefinitions>
</Grid>
```

## Complete Page Example

```xml
<ContentPage xmlns="http://schemas.microsoft.com/dotnet/2021/maui"
             x:Class="MyApp.ProfilePage"
             Title="Profile">
    
    <Grid Padding="20" RowSpacing="20">
        <Grid.RowDefinitions>
            <RowDefinition Height="Auto" />   <!-- Avatar section -->
            <RowDefinition Height="Auto" />   <!-- Info section -->
            <RowDefinition Height="*" />      <!-- Bio/content -->
            <RowDefinition Height="Auto" />   <!-- Buttons -->
        </Grid.RowDefinitions>
        
        <!-- Row 0: Avatar and Name -->
        <Grid Grid.Row="0">
            <Grid.ColumnDefinitions>
                <ColumnDefinition Width="Auto" />
                <ColumnDefinition Width="*" />
            </Grid.ColumnDefinitions>
            
            <Image Grid.Column="0" 
                   Source="avatar.png" 
                   WidthRequest="80" 
                   HeightRequest="80"
                   Aspect="AspectFill">
                <Image.Clip>
                    <EllipseGeometry Center="40,40" RadiusX="40" RadiusY="40" />
                </Image.Clip>
            </Image>
            
            <VerticalStackLayout Grid.Column="1" Padding="15,0,0,0" VerticalOptions="Center">
                <Label Text="John Doe" FontSize="24" FontAttributes="Bold" />
                <Label Text="Software Developer" FontSize="14" TextColor="Gray" />
            </VerticalStackLayout>
        </Grid>
        
        <!-- Row 1: Stats -->
        <Grid Grid.Row="1" ColumnSpacing="10">
            <Grid.ColumnDefinitions>
                <ColumnDefinition Width="*" />
                <ColumnDefinition Width="*" />
                <ColumnDefinition Width="*" />
            </Grid.ColumnDefinitions>
            
            <Frame Grid.Column="0" BackgroundColor="LightBlue" Padding="10">
                <VerticalStackLayout HorizontalOptions="Center">
                    <Label Text="42" FontSize="20" FontAttributes="Bold" HorizontalOptions="Center" />
                    <Label Text="Posts" FontSize="12" HorizontalOptions="Center" />
                </VerticalStackLayout>
            </Frame>
            
            <Frame Grid.Column="1" BackgroundColor="LightGreen" Padding="10">
                <VerticalStackLayout HorizontalOptions="Center">
                    <Label Text="1.2K" FontSize="20" FontAttributes="Bold" HorizontalOptions="Center" />
                    <Label Text="Followers" FontSize="12" HorizontalOptions="Center" />
                </VerticalStackLayout>
            </Frame>
            
            <Frame Grid.Column="2" BackgroundColor="LightCoral" Padding="10">
                <VerticalStackLayout HorizontalOptions="Center">
                    <Label Text="350" FontSize="20" FontAttributes="Bold" HorizontalOptions="Center" />
                    <Label Text="Following" FontSize="12" HorizontalOptions="Center" />
                </VerticalStackLayout>
            </Frame>
        </Grid>
        
        <!-- Row 2: Bio -->
        <Frame Grid.Row="2" BorderColor="LightGray">
            <ScrollView>
                <Label Text="Lorem ipsum dolor sit amet, consectetur adipiscing elit. Sed do eiusmod tempor incididunt ut labore et dolore magna aliqua." />
            </ScrollView>
        </Frame>
        
        <!-- Row 3: Action Buttons -->
        <Grid Grid.Row="3" ColumnSpacing="10">
            <Grid.ColumnDefinitions>
                <ColumnDefinition Width="*" />
                <ColumnDefinition Width="*" />
            </Grid.ColumnDefinitions>
            
            <Button Grid.Column="0" Text="Edit Profile" />
            <Button Grid.Column="1" Text="Settings" />
        </Grid>
    </Grid>
</ContentPage>
```

## Best Practices

1. ✅ **Use Grid for complex layouts**: Most flexible and fast
2. ✅ **Use StackLayouts for simple lists**: Simpler than Grid
3. ✅ **Avoid deep nesting**: Flatter = better performance
4. ✅ **Use `*` (star) sizing**: Adapts to screen size
5. ✅ **Test on multiple screen sizes**: Use emulators with different resolutions
6. ✅ **Prefer `VerticalStackLayout` over `StackLayout`**: Better performance in .NET MAUI

## Performance Tips

- ⚡ **Grid** is highly optimized - use it
- ⚡ **Avoid AbsoluteLayout** unless absolutely needed
- ⚡ **Minimize nested layouts** - flatten where possible
- ⚡ **Use `Auto` sizing sparingly** - can be expensive with many children

## Common Mistakes

❌ **Forgetting row/column indices**
```xml
<!-- Control not visible because it's in row 0, column 0 by default -->
<Label Text="I'm overlapping!" />
```

❌ **Star sizing without constraints**
```xml
<!-- ScrollView with star-sized content won't scroll -->
<ScrollView>
    <Grid RowDefinitions="*">  <!-- Remove * inside ScrollView -->
```

❌ **Hardcoded sizes**
```xml
<!-- Not responsive -->
<Frame WidthRequest="300" HeightRequest="200">
```

## Key Takeaways

- **Grid**: Best for complex responsive layouts with rows/columns
- **StackLayouts**: Simple vertical or horizontal stacking
- **FlexLayout**: Responsive wrapping layouts
- Row/Column sizing: `Auto` (content), `*` (proportional), `100` (fixed)
- Use `Padding` (inside), `Margin` (outside), `Spacing` (between)
- Nest layouts for complex UIs
- Always test on different screen sizes

## Next Steps

- Learn [CollectionView](06-CollectionView.md) for scrollable lists
- Explore [Styling & Theming](10-Styling-Theming.md) for consistent design
- Practice with [04.LayoutsAndCollections](../04.LayoutsAndCollections/)

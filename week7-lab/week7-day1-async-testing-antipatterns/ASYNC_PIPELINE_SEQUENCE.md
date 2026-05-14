# Async Pipeline Sequence Diagram

Students should complete or adapt this diagram for the Part 4 deliverable.

```mermaid
sequenceDiagram
    participant Client
    participant Pipeline
    participant Profile
    participant Pricing
    participant Inventory
    participant Recommendations

    Client->>Pipeline: request
    Pipeline->>Profile: supplyAsync(profile)
    Pipeline->>Pricing: supplyAsync(pricing)
    Pipeline->>Inventory: supplyAsync(inventory)
    Pipeline->>Recommendations: supplyAsync(recommendations)

    Profile-->>Pipeline: result
    Pricing-->>Pipeline: result
    Inventory-->>Pipeline: timeout -> fallback
    Recommendations-->>Pipeline: failure -> fallback

    Pipeline->>Pipeline: allOf() completion barrier
    Pipeline->>Pipeline: aggregate explicit results
    Pipeline-->>Client: combined response + fallback flag
```

## Explanation Prompts

- Which tasks fan out independently?
- What coordinates fan-in?
- Where does timeout become fallback?
- Where does failure become fallback?
- How does the response reveal that fallback was used?

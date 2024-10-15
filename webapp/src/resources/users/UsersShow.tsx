import {SimpleShowLayout, Show, NumberField} from "react-admin";


export const UsersShow = () => {
    return (
        <Show resource="users">
            <SimpleShowLayout>
                <NumberField source="id"/>

            </SimpleShowLayout>
        </Show>
    );
};
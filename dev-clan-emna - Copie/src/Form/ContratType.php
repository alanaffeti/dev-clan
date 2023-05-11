<?php

namespace App\Form;

use App\Entity\Contrat;
use Symfony\Component\Form\AbstractType;
use Symfony\Component\Form\Extension\Core\Type\DateType;
use Symfony\Component\Form\Extension\Core\Type\MoneyType;
use Symfony\Component\Form\Extension\Core\Type\TextType;
use Symfony\Component\Form\FormBuilderInterface;
use Symfony\Component\OptionsResolver\OptionsResolver;
use Symfony\Component\Validator\Constraints\NotBlank;
use Symfony\Component\Validator\Constraints\Length;
use Symfony\Component\Validator\Constraints\Regex;

class ContratType extends AbstractType
{
    public function buildForm(FormBuilderInterface $builder, array $options): void
    {
        $builder
        ->add('datedebut', DateType::class, [
            'widget' => 'single_text',
           ])
           ->add('datefin', DateType::class, [
            'widget' => 'single_text',
           ])
           ->add('prix', MoneyType::class, [
            'property_path' => 'prix'
        ])
           ->add('type', TextType::class ,[
            'constraints' => [
            new NotBlank(['message' => 'Veuillez saisir votre adresse email.']),],])
           ->add('nomconducteur', TextType::class, [
            'constraints' => [
                new Regex([
                    'pattern' => '/^[a-zA-Z]+$/',
                    'message' => 'Le nom doit contenir seulement des lettres.'
                ])
            ]
        ])
        
        ->add('prenomconducteur', TextType::class, [
            'constraints' => [
                new Regex([
                    'pattern' => '/^[a-zA-Z]+$/',
                    'message' => 'Le prénom doit contenir seulement des lettres.'
                ])
            ]
        ])
           ->add('cin', null, [
            'constraints' => [

             new  Regex(
                  pattern:"/^\d{8}$/",
                 message:"Le CIN doit contenir exactement 8 chiffres."
             ),
             new NotBlank([
                        'message' => 'Veuillez saisir votre cin'
                    ])
            ],

        ])           
        ->add('region', TextType::class,[
            'constraints' => [
            new NotBlank(['message' => 'Veuillez saisir votre adresse email.']),],])
            
        ;
    }

    public function configureOptions(OptionsResolver $resolver): void
    {
        $resolver->setDefaults([
            'data_class' => Contrat::class,
        ]);
    }
}
